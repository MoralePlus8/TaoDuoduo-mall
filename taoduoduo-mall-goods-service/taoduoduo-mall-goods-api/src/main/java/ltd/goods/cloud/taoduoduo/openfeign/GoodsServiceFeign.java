package ltd.goods.cloud.taoduoduo.openfeign;

import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.entity.Goods;
import ltd.goods.cloud.taoduoduo.dto.UpdateStockNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "taoduoduo-mall-goods-service", path = "/goods/admin")
public interface GoodsServiceFeign {

    @GetMapping(value = "/{id}")
    Goods getGoods(@PathVariable("id") Long goodsId);

    @GetMapping(value = "/listByGoodsIds")
    Result listByGoodsIds(@RequestBody List<Long> goodsIds);

    @PutMapping(value = "/updateStock")
    Result updateStock(@RequestBody UpdateStockNumDTO updateStockNumDTO);

}
