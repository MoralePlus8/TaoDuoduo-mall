package ltd.shopcart.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.shopcart.cloud.taoduoduo.entity.ShoppingCartItem;

public interface ShoppingCartService {
    PageResult<ShoppingCartItem> pageQuery(Integer pageNumber);

    void save(CartItemRequest cartItemRequest, Long userId);

}
