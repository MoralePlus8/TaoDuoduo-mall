package ltd.goods.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.enums.CategoryLevelEnum;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryRequest;
import ltd.goods.cloud.taoduoduo.dto.StockNumRequest;
import ltd.goods.cloud.taoduoduo.dto.StockNumUpdateRequest;
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

import java.util.Date;
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

    @Override
    public void addTags(Long goodsId, List<String> tagsName) {

        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new GoodsNotExistException();
        }
        goods.setUpdateTime(new Date());
        goodsMapper.updateById(goods);

        for(String tag : tagsName) {
            GoodsTag goodsTag = new GoodsTag(goodsId, tag);
            goodsTagMapper.insert(goodsTag);
        }
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
    public PageResult<GoodsDoc> pageQuery(GoodsPageQueryRequest goodsPageQueryRequest) {

        String keyword = goodsPageQueryRequest.getKeyword();
        List<Integer> categoryIds = goodsPageQueryRequest.getCategoryIds();
        Double minPrice = goodsPageQueryRequest.getMinPrice();
        Double maxPrice = goodsPageQueryRequest.getMaxPrice();
        List<String> tags = goodsPageQueryRequest.getTags();
        int page = goodsPageQueryRequest.getPageNumber() - 1; // PageRequest的页码从0开始
        int size = goodsPageQueryRequest.getPageSize();
        String sortType = goodsPageQueryRequest.getSortType();
        String priceOrder = goodsPageQueryRequest.getPriceOrder();

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

        List<GoodsDoc> goodsList = sortQueryByNativeSearch(builder, sortType, priceOrder);

        return new PageResult<>(goodsList, goodsList.size(), size, page + 1);


    }

    private List<GoodsDoc> sortQueryByNativeSearch(NativeSearchQueryBuilder builder, String sortType, String sortOrder){

        if ("sales".equalsIgnoreCase(sortType)) {
            builder.withSorts(SortBuilders.fieldSort(GoodsDoc.IndexAttributes.SALES_VOLUME).order(SortOrder.DESC));
        } else if ("price".equalsIgnoreCase(sortType)) {
            SortOrder order = "asc".equalsIgnoreCase(sortOrder) ? SortOrder.ASC : SortOrder.DESC;
            builder.withSorts(SortBuilders.fieldSort(GoodsDoc.IndexAttributes.PRICE).order(order));
        } else {
            // 默认按 _score 排序（综合评分）
            builder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        }

        SearchHits<GoodsDoc> searchHits = elasticsearchRestTemplate.search(builder.build(), GoodsDoc.class);
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

    }

    @Override
    public void batchUpdateSellStatus(List<Long> goodsIds, Boolean sellStatus) {
        if (goodsIds == null || goodsIds.isEmpty()) {
            throw new ParamErrorException();
        }

        goodsIds.forEach(id -> {
            Goods goods = new Goods();
            goods.setGoodsId(id);
            goods.setStatus(sellStatus);
            goodsMapper.updateById(goods);
        });

    }

    @Override
    public void updateStock(StockNumUpdateRequest stockNumUpdateRequest) {
        if (stockNumUpdateRequest == null || stockNumUpdateRequest.getStockNumRequests() == null || stockNumUpdateRequest.getStockNumRequests().isEmpty()) {
            throw new ParamErrorException();
        }

        for (StockNumRequest stockNumRequest : stockNumUpdateRequest.getStockNumRequests()) {
            Goods goods = new Goods();
            goods.setGoodsId(stockNumRequest.getGoodsId());
            goods.setStockNum(stockNumRequest.getGoodsCount());
            goodsMapper.updateById(goods);
        }

    }

    @Override
    public List<Goods> getGoodsByIds(List<Long> goodsIds) {
        return goodsMapper.selectBatchIds(goodsIds);
    }
}
