package ltd.goods.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryRequest;
import ltd.goods.cloud.taoduoduo.dto.StockNumUpdateRequest;
import ltd.common.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.doc.GoodsDoc;

import javax.validation.Valid;
import java.util.List;

public interface GoodsService {

    String save(Goods goods);

    void update(Goods goods);

    void addTags(Long goodsId, List<String> tagsName);

    Goods getGoodsById(Long id);

    PageResult<GoodsDoc> pageQuery(@Valid GoodsPageQueryRequest goodsPageQueryRequest);

    void batchUpdateSellStatus(List<Long> goodIds, Boolean sellStatus);

    void updateStock(StockNumUpdateRequest stockNumUpdateRequest);

    List<Goods> getGoodsByIds(List<Long> goodsIds);

    Goods getGoodsOnSale(Long goodsId);
}
