package ltd.shopcart.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.page.PageMethod;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.entity.Goods;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.goods.cloud.taoduoduo.openfeign.GoodsServiceFeign;
import ltd.shopcart.cloud.taoduoduo.dto.CartItemRequest;
import ltd.common.cloud.taoduoduo.entity.ShoppingCartItem;
import ltd.shopcart.cloud.taoduoduo.mapper.ShoppingCartMapper;
import ltd.shopcart.cloud.taoduoduo.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Integer PAGE_SIZE = 5;

    private final ShoppingCartMapper shoppingCartMapper;

    private final GoodsServiceFeign goodsService;


    @Override
    public PageResult<ShoppingCartItem> pageQuery(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }

        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());

        PageMethod.startPage(pageNumber, PAGE_SIZE);
        List<ShoppingCartItem> page = shoppingCartMapper.selectList(queryWrapper);

        return new PageResult<>(page, page.size(), PAGE_SIZE, pageNumber);
    }

    @Override
    public void save(CartItemRequest cartItemRequest) {

        if(cartItemRequest.getGoodsCount() < 1){
            throw new OutOfStockException();
        }

        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());
        queryWrapper.eq(ShoppingCartItem.TableAttributes.GOODS_ID, cartItemRequest.getGoodsId());

        ShoppingCartItem existingItem = shoppingCartMapper.selectOne(queryWrapper);
        if (existingItem != null) {
            throw new ShoppingCartItemExistException();
        }

        Goods goods = goodsService.getGoods(cartItemRequest.getGoodsId());
        if(goods.getStockNum() < cartItemRequest.getGoodsCount()){
            throw new OutOfStockException();
        }

        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setGoodsId(cartItemRequest.getGoodsId());
        shoppingCartItem.setUserId(UserContextUtil.getUserId());
        shoppingCartItem.setGoodsCount(cartItemRequest.getGoodsCount());
        shoppingCartMapper.insert(shoppingCartItem);

    }




    @Override
    public void update(CartItemRequest cartItemRequest) {

        if(cartItemRequest.getGoodsCount() < 1){
            throw new OutOfStockException();
        }

        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());
        queryWrapper.eq(ShoppingCartItem.TableAttributes.GOODS_ID, cartItemRequest.getGoodsId());

        ShoppingCartItem existingItem = shoppingCartMapper.selectOne(queryWrapper);
        if (existingItem == null) {
            throw new DataNotExistException();
        }

        Goods goods = goodsService.getGoods(cartItemRequest.getGoodsId());
        if(goods.getStockNum() < cartItemRequest.getGoodsCount()){
            throw new OutOfStockException();
        }

        existingItem.setGoodsCount(cartItemRequest.getGoodsCount());
        existingItem.setUpdateTime(new Date());
        shoppingCartMapper.updateById(existingItem);
    }

    @Override
    public ShoppingCartItem getCartItemById(Long cartItemId) {

        ShoppingCartItem shoppingCartItem = shoppingCartMapper.selectById(cartItemId);
        if (shoppingCartItem == null) {
            throw new DataNotExistException();
        }

        return shoppingCartItem;
    }

    @Override
    public void deleteById(Long cartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartMapper.selectById(cartItemId);
        if(!Objects.equals(UserContextUtil.getUserId(), shoppingCartItem.getUserId())){
            throw new AccessDeniedException();
        }
        if(shoppingCartMapper.deleteById(cartItemId)<1){
            throw new DataNotExistException();
        }
    }

    @Override
    public void deleteByIds(List<Long> cartItemIds) {
        if(cartItemIds == null || cartItemIds.isEmpty()){
            throw new DataNotExistException();
        }
        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());
        queryWrapper.in(ShoppingCartItem.TableAttributes.CART_ITEM_ID, cartItemIds);
        int cnt = shoppingCartMapper.delete(queryWrapper);
        if(cnt < 1 || cnt != cartItemIds.size()){
            throw new DataBaseErrorException();
        }
    }

    @Override
    public List<ShoppingCartItem> getMyCartItems() {
        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());
        return shoppingCartMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShoppingCartItem> getCartItemsForSettle(List<Long> cartItemIds) {
        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ShoppingCartItem.TableAttributes.USER_ID, UserContextUtil.getUserId());
        queryWrapper.in(ShoppingCartItem.TableAttributes.CART_ITEM_ID, cartItemIds);
        List<ShoppingCartItem> shoppingCartItems = shoppingCartMapper.selectList(queryWrapper);
        if(cartItemIds.size() != shoppingCartItems.size()){
            throw new DataBaseErrorException();
        }
        return shoppingCartItems;
    }

    @Override
    public List<ShoppingCartItem> getCartItemsByIds(List<Long> cartItemIds) {
        QueryWrapper<ShoppingCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ShoppingCartItem.TableAttributes.CART_ITEM_ID, cartItemIds);
        return shoppingCartMapper.selectList(queryWrapper);
    }
}
