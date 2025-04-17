package ltd.goods.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.CategoryLevelEnum;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryDTO;
import ltd.goods.cloud.taoduoduo.dto.StockNumDTO;
import ltd.goods.cloud.taoduoduo.dto.StockNumUpdateDTO;
import ltd.goods.cloud.taoduoduo.entity.Category;
import ltd.goods.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.entity.GoodsTag;
import ltd.goods.cloud.taoduoduo.entity.doc.GoodsDoc;
import ltd.goods.cloud.taoduoduo.mapper.CategoryMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsMapper;
import ltd.goods.cloud.taoduoduo.mapper.GoodsTagMapper;
import ltd.goods.cloud.taoduoduo.service.GoodsService;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    private final CategoryMapper categoryMapper;

    private final GoodsTagMapper goodsTagMapper;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    @Transactional
    public String save(Goods goods) {
        Category category = categoryMapper.selectById(goods.getCategoryId());
        /* 分类不存在或者不是第三级分类 */
        if (category == null || category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THIRD.getLevel()) {
            throw new GoodsCategoryErrorException();
        }
        goodsMapper.insert(goods);
        List<String> tags = goods.getTags();
        for(String tag : tags) {
            goodsTagMapper.insert(new GoodsTag(goods.getGoodsId(), tag));
        }
        return goods.getGoodsId().toString();
    }

    @Override
    public void update(Goods goods) {
        Category category = categoryMapper.selectById(goods.getCategoryId());
        /* 分类不存在或者不是第三级分类 */
        if (category == null || category.getCategoryLevel().intValue() != CategoryLevelEnum.LEVEL_THIRD.getLevel()) {
            throw new GoodsCategoryErrorException();
        }

        Goods existingGoods = goodsMapper.selectById(goods.getGoodsId());
        if (existingGoods == null) {
            throw new GoodsNotExistException();
        }

        if(goods.getTags()!=null) {
            updateTags(goods);
        }

        if (goodsMapper.updateById(goods) > 0) {
            return;
        }

        throw new DataBaseErrorException();
    }

    private void updateTags(Goods goods) {
        QueryWrapper<GoodsTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(GoodsTag.TableAttributes.GOODS_ID, goods.getGoodsId());
        goodsTagMapper.delete(queryWrapper);
        List<String> tags = goods.getTags();
        for(String tag : tags) {
            goodsTagMapper.insert(new GoodsTag(goods.getGoodsId(), tag));
        }
    }

    @Override
    public Goods getGoodsById(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new DataNotExistException();
        }

        return goods;
    }


    @Override
    public Goods getGoodsOnSale(Long id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new DataNotExistException();
        }
        if (Boolean.TRUE.equals(goods.getStatus())) {
            throw new GoodsPutDownException();
        }

        return goods;
    }

    @Override
    public PageResult<Goods> pageQuery(GoodsPageQueryDTO goodsPageQueryDTO) {

        String keyword = goodsPageQueryDTO.getKeyword();
        List<Integer> categoryIds = goodsPageQueryDTO.getCategoryIds();
        Double minPrice = goodsPageQueryDTO.getMinPrice();
        Double maxPrice = goodsPageQueryDTO.getMaxPrice();
        List<String> tags = goodsPageQueryDTO.getTags();
        int page = goodsPageQueryDTO.getPageNumber() - 1; // PageRequest的页码从0开始
        int size = goodsPageQueryDTO.getPageSize();
        String sortType = goodsPageQueryDTO.getSortType();
        String priceOrder = goodsPageQueryDTO.getPriceOrder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery(GoodsDoc.IndexAttributes.STATUS, false)); // 只查询上架商品

        // 关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            MultiMatchQueryBuilder keywordQuery = QueryBuilders.multiMatchQuery(
                    keyword,
                    GoodsDoc.IndexAttributes.GOODS_NAME,
                    GoodsDoc.IndexAttributes.GOODS_INTRO);

            boolQuery.must(keywordQuery);
        }

        // 分类筛选
        if (categoryIds != null && !categoryIds.isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery(GoodsDoc.IndexAttributes.CATEGORY_ID_LIST, categoryIds));
        }

        // 价格范围
        if (minPrice != null || maxPrice != null) {
            RangeQueryBuilder priceRange = QueryBuilders.rangeQuery(GoodsDoc.IndexAttributes.PRICE);
            if (minPrice != null) priceRange.gte(minPrice);
            if (maxPrice != null) priceRange.lte(maxPrice);
            boolQuery.filter(priceRange);
        }

        // 标签筛选（支持多个标签同时满足）
        if (tags != null && !tags.isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery(GoodsDoc.IndexAttributes.TAGS, tags));
        }

        Pageable pageable = PageRequest.of(page, size);
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable);

        List<Goods> goodsList = sortQueryByNativeSearch(builder, sortType, priceOrder);

        return new PageResult<>(goodsList, goodsList.size(), size, page + 1);


    }

    private List<Goods> sortQueryByNativeSearch(NativeSearchQueryBuilder builder, String sortType, String sortOrder){

        if ("sales".equalsIgnoreCase(sortType)) {
            builder.withSorts(SortBuilders.fieldSort("sales").order(SortOrder.DESC));
        } else if ("price".equalsIgnoreCase(sortType)) {
            SortOrder order = "asc".equalsIgnoreCase(sortOrder) ? SortOrder.ASC : SortOrder.DESC;
            builder.withSorts(SortBuilders.fieldSort("price").order(order));
        } else {
            // 默认按 _score 排序（综合评分）
            builder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        }

        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(builder.build(), Goods.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

    }

    @Override
    public void batchUpdateSellStatus(BatchIdDTO batchIdDTO, Boolean sellStatus) {
        if (batchIdDTO == null || batchIdDTO.getIds().isEmpty()) {
            throw new ParamErrorException();
        }

        batchIdDTO.getIds().forEach(id -> {
            UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq(Goods.TableAttributes.GOODS_ID, id).set(Goods.TableAttributes.STATUS, sellStatus);
            goodsMapper.update(null, updateWrapper);
        });

        throw new DataBaseErrorException();
    }

    @Override
    public void updateStock(StockNumUpdateDTO stockNumUpdateDTO) {
        if (stockNumUpdateDTO == null || stockNumUpdateDTO.getStockNumDTOS() == null || stockNumUpdateDTO.getStockNumDTOS().isEmpty()) {
            throw new ParamErrorException();
        }

        for (StockNumDTO stockNumDTO : stockNumUpdateDTO.getStockNumDTOS()) {
            Goods goods = new Goods();
            goods.setGoodsId(stockNumDTO.getGoodsId());
            goods.setStockNum(stockNumDTO.getGoodsCount());
            goodsMapper.updateById(goods);
        }

        throw new DataBaseErrorException();
    }

    @Override
    public List<Goods> getGoodsByIds(List<Long> goodsIds) {
        return goodsMapper.selectBatchIds(goodsIds);
    }
}
