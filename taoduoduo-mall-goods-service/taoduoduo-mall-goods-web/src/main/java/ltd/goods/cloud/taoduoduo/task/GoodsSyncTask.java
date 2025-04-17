package ltd.goods.cloud.taoduoduo.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.entity.GoodsTag;
import ltd.goods.cloud.taoduoduo.entity.doc.GoodsDoc;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsTagMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoodsSyncTask {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final GoodsMapper goodsMapper;

    private final GoodsTagMapper goodsTagMapper;

    private final CategoryMapper categoryMapper;


    @Scheduled(cron = "0 0 2 * * ?")
    public void syncGoodsToES() {

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(Goods.TableAttributes.UPDATE_TIME, startTime, endTime);

        List<Goods> updatedGoods = goodsMapper.selectList(queryWrapper);

        for(Goods goods: updatedGoods) {

            // Fetch tags for the goods
            QueryWrapper<GoodsTag> tagQueryWrapper = new QueryWrapper<>();
            tagQueryWrapper.eq(Goods.TableAttributes.GOODS_ID, goods.getGoodsId());
            List<String> tags = goodsTagMapper.selectList(tagQueryWrapper)
                    .stream()
                    .map(GoodsTag::getTagName)
                    .collect(Collectors.toList());
            goods.setTags(tags);

            GoodsDoc goodsDoc = new GoodsDoc();
            BeanUtils.copyProperties(goods, goodsDoc);

            // Fetch category information
            QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
            Long categoryLevel3Id = goods.getCategoryId();
            categoryQueryWrapper.eq(Category.TableAttributes.CATEGORY_ID, categoryLevel3Id);
            Long categoryLevel2Id = categoryMapper.selectOne(categoryQueryWrapper).getParentId();

            categoryQueryWrapper = new QueryWrapper<>();
            categoryQueryWrapper.eq(Category.TableAttributes.CATEGORY_ID, categoryLevel2Id);
            Long categoryLevel1Id = categoryMapper.selectOne(categoryQueryWrapper).getParentId();

            goodsDoc.setCategoryLevel1Id(categoryLevel1Id);
            goodsDoc.setCategoryLevel2Id(categoryLevel2Id);
            goodsDoc.setCategoryLevel3Id(categoryLevel3Id);
            goodsDoc.setCategoryIdList(Arrays.asList(categoryLevel1Id, categoryLevel2Id, categoryLevel3Id));

            // Save to Elasticsearch
            elasticsearchRestTemplate.save(goodsDoc);

        }

    }

}
