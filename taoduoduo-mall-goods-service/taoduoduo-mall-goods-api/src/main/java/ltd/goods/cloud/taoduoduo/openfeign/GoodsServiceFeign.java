package ltd.goods.cloud.taoduoduo.openfeign;

import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.goods.cloud.taoduoduo.dto.UpdateStockNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "taoduoduo-mall-goods-service", path = "/goods/admin")
public interface GoodsServiceFeign {

    @GetMapping(value = "/admin/detail/{id}")
    Result getGoodsDetail(@PathVariable("id") Long goodsId);

    @GetMapping(value = "/admin/listByGoodsIds")
    Result listByGoodsIds(@RequestBody List<Long> goodsIds);

    @PutMapping(value = "/admin/updateStock")
    Result updateStock(@RequestBody UpdateStockNumDTO updateStockNumDTO);

}
