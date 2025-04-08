package ltd.shopcart.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.shopcart.cloud.taoduoduo.dto.ShoppingCartItemDTO;
import ltd.shopcart.cloud.taoduoduo.entity.ShoppingCartItem;

public interface ShoppingCartService {
    PageResult<ShoppingCartItem> pageQuery(Integer pageNumber);

    void save(ShoppingCartItemDTO shoppingCartItemDTO, Long userId);
}
