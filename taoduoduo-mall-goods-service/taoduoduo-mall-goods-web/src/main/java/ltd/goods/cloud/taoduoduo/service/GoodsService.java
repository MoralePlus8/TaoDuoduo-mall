package ltd.goods.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.goods.cloud.taoduoduo.dto.BatchIdDTO;
import ltd.goods.cloud.taoduoduo.dto.GoodsPageQueryDTO;
import ltd.goods.cloud.taoduoduo.dto.StockNumUpdateDTO;
import ltd.goods.cloud.taoduoduo.entity.Goods;

import javax.validation.Valid;
import java.util.List;

public interface GoodsService {

    String save(Goods goods);

    void update(Goods goods);

    Goods getGoodsById(Long id);

    PageResult<Goods> pageQuery(@Valid GoodsPageQueryDTO goodsPageQueryDTO);

    void batchUpdateSellStatus(BatchIdDTO batchIdDTO, Boolean sellStatus);

    void updateStock(StockNumUpdateDTO stockNumUpdateDTO);

    List<Goods> getGoodsByIds(List<Long> goodsIds);

    Goods getGoodsOnSale(Long goodsId);
}
