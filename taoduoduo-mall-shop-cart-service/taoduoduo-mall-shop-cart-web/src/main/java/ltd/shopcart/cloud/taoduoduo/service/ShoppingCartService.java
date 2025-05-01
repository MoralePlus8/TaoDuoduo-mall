package ltd.shopcart.cloud.taoduoduo.service;


import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.common.cloud.taoduoduo.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartService {
    PageResult<ShoppingCartItem> pageQuery(Integer pageNumber);

    void save(CartItemRequest cartItemRequest);

    void update(CartItemRequest cartItemRequest);

    ShoppingCartItem getCartItemById(Long cartItemId);

    void deleteById(Long cartItemId);

    void deleteByIds(List<Long> cartItemIds);

    List<ShoppingCartItem> getMyCartItems();

    List<ShoppingCartItem> getCartItemsForSettle(List<Long> cartItemIds);

    List<ShoppingCartItem> getCartItemsByIds(List<Long> cartItemIds);



}
