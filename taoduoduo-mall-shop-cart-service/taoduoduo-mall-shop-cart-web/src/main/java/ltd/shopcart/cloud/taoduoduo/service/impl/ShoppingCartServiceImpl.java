package ltd.shopcart.cloud.taoduoduo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.exception.ShoppingCartItemExistException;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.shopcart.cloud.taoduoduo.entity.ShoppingCartItem;
import ltd.shopcart.cloud.taoduoduo.mapper.ShoppingCartMapper;
import ltd.shopcart.cloud.taoduoduo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Integer PAGE_SIZE = 5;

    private final ShoppingCartMapper shoppingCartMapper;



    @Override
    public PageResult<ShoppingCartItem> pageQuery(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }

        PageMethod.startPage(pageNumber, PAGE_SIZE);
        Page<ShoppingCartItem> page = shoppingCartMapper.pageQuery(pageNumber, PAGE_SIZE);

        return new PageResult<>(page.getResult(), page.getTotal(), page.getPageSize(), page.getPageNum());
    }

    @Override
    public void save(CartItemRequest cartItemRequest, Long userId) {
        ShoppingCartItem existingItem = shoppingCartMapper.findByIdAndUserId(cartItemRequest.getGoodsId(), userId);
        if (existingItem != null) {
            throw new ShoppingCartItemExistException();
        }


    }
}
