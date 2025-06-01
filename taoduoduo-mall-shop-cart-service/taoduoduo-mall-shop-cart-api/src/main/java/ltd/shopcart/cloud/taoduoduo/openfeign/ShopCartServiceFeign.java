package ltd.shopcart.cloud.taoduoduo.openfeign;

import ltd.common.cloud.taoduoduo.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "taoduoduo-mall-cart-service", path = "/shopcart")
public interface ShopCartServiceFeign {

    @GetMapping(value = "/list")
    Result getCartItemsByIds(@RequestBody List<Long> cartItemIds);

    @DeleteMapping(value = "/batchDelete")
    Result batchDelete(@RequestBody List<Long> cartItemIds);

}
