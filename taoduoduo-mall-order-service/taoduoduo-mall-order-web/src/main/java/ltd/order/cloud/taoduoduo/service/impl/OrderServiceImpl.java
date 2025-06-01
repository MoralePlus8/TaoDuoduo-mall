package ltd.order.cloud.taoduoduo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.page.PageMethod;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.dto.Result;
import ltd.common.cloud.taoduoduo.entity.*;
import ltd.common.cloud.taoduoduo.exception.*;
import ltd.common.cloud.taoduoduo.util.NumberUtil;
import ltd.common.cloud.taoduoduo.util.UserContextUtil;
import ltd.goods.cloud.taoduoduo.dto.StockNumDTO;
import ltd.goods.cloud.taoduoduo.dto.UpdateStockNumDTO;
import ltd.goods.cloud.taoduoduo.openfeign.GoodsServiceFeign;
import ltd.order.cloud.taoduoduo.mapper.OrderItemMapper;
import ltd.order.cloud.taoduoduo.mapper.OrderMapper;
import ltd.order.cloud.taoduoduo.service.OrderService;
import ltd.shopcart.cloud.taoduoduo.openfeign.ShopCartServiceFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    private final GoodsServiceFeign goodsService;

    private final ShopCartServiceFeign shopCartService;

    @Override
    public Order getOrderDetailByOrderId(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            throw new OrderNotExistException();
        }
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OrderItem.TableAttributes.ORDER_ID, orderId);
        order.setOrderItems(orderItemMapper.selectList(queryWrapper));
        return order;
    }

    @Override
    public Order getOrderDetailByOrderNo(String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Order.TableAttributes.ORDER_NO, orderNo);
        Order order = getOrderByNoAndCheckUid(orderNo);
        order.setOrderItems(orderItemMapper.selectList(
                new QueryWrapper<OrderItem>().eq(OrderItem.TableAttributes.ORDER_ID, order.getOrderId())));
        return order;
    }

    @Override
    public PageResult<Order> getMyOrders(Integer pageNum, Integer orderStatus) {
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Order.TableAttributes.USER_ID, UserContextUtil.getUserId());
        if(orderStatus != null) queryWrapper.eq(Order.TableAttributes.ORDER_STATUS, orderStatus);
        int total = orderMapper.selectCount(queryWrapper);
        PageMethod.startPage(pageNum, 5);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        return new PageResult<>(orders, total, 5, pageNum);
    }

    @Override
    public void cancelOrder(String orderNo) {
        Order order = getOrderByNoAndCheckUid(orderNo);
        if (order.getOrderStatus() == Order.OrderStatus.ORDER_SUCCESS
                || order.getOrderStatus() == Order.OrderStatus.ORDER_CLOSED_BY_MALLUSER
                || order.getOrderStatus() == Order.OrderStatus.ORDER_CLOSED_BY_EXPIRED
                || order.getOrderStatus() == Order.OrderStatus.ORDER_CLOSED_BY_JUDGE) {
            throw new OrderStatusException();
        }
        order.setOrderStatus(Order.OrderStatus.ORDER_CLOSED_BY_MALLUSER);
        order.setUpdateTime(new Date());
        orderMapper.updateById(order);
    }

    @Override
    public void finishOrder(String orderNo) {
        Order order = getOrderByNoAndCheckUid(orderNo);
        if(order.getOrderStatus() != Order.OrderStatus.ORDER_EXPRESS){
            throw new OrderStatusException();
        }
        order.setOrderStatus(Order.OrderStatus.ORDER_SUCCESS);
        order.setUpdateTime(new Date());
        orderMapper.updateById(order);
    }

    @Override
    public void paySuccess(String orderNo, int payType) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq(Order.TableAttributes.ORDER_NO, orderNo));
        if(order == null){
            throw new OrderNotExistException();
        }
        if(order.getOrderStatus() == Order.OrderStatus.ORDER_PRE_PAY){
            throw new OrderStatusException();
        }
        order.setOrderStatus(Order.OrderStatus.ORDER_PAID);
        order.setPayType((byte)payType);
        order.setPayStatus(Order.PayStatus.PAY_SUCCESS);
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public String saveOrder(Long addressId, List<Long> cartItemIds) {
        Result cartItemsResult = shopCartService.getCartItemsByIds(cartItemIds);
        if(cartItemsResult == null || cartItemsResult.getResultCode() != 200 || !(cartItemsResult.getData() instanceof List)){
            throw new OrderNotExistException();
        }
        List<ShoppingCartItem> cartItems = (List<ShoppingCartItem>) cartItemsResult.getData();
        if(cartItems.isEmpty()){
            throw new DataNotExistException();
        }

        List<Long> itemIds = cartItems.stream().map(ShoppingCartItem::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = cartItems.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
        if(itemIds.isEmpty() || goodsIds.isEmpty()){
            throw new DataNotExistException();
        }

        Result goodsResult = goodsService.listByGoodsIds(goodsIds);
        if(goodsResult == null || goodsResult.getResultCode() != 200 || !(goodsResult.getData() instanceof List)){
            throw new RpcException();
        }
        List<Goods> goodsList = (List<Goods>) goodsResult.getData();
        if(goodsList.isEmpty()){
            throw new DataNotExistException();
        }
        Map<Long, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        for(Goods goods : goodsList){
            if(goods.getStatus()) {
                throw new GoodsNotExistException();
            }
        }
        if(goodsList.size() != cartItems.size()){
            throw new RpcException();
        }

        Result deleteResult = shopCartService.batchDelete(itemIds);
        if(deleteResult == null || deleteResult.getResultCode() != 200 || !(deleteResult.getData() instanceof List)){
            throw new RpcException();
        }
        UpdateStockNumDTO updateStockNumDTO = new UpdateStockNumDTO();
        List<StockNumDTO> stockNums = cartItems.stream().map(item->{
            StockNumDTO stockNumDTO = new StockNumDTO();
            stockNumDTO.setGoodsId(item.getGoodsId());
            stockNumDTO.setGoodsCount(item.getGoodsCount());
            return stockNumDTO;
        }).collect(Collectors.toList());
        updateStockNumDTO.setStockNumRequests(stockNums);
        Result updateGoodsStockResult = goodsService.updateStock(updateStockNumDTO);
        if(updateGoodsStockResult == null || updateGoodsStockResult.getResultCode() != 200){
            throw new RpcException();
        }
        String orderNo = NumberUtil.genOrderNo();
        BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(UserContextUtil.getUserId());
        order.setAddressId(addressId);
        for(ShoppingCartItem cartItem : cartItems) {
            BigDecimal sellingPrice = goodsMap.get(cartItem.getGoodsId()).getSellingPrice();
            totalPrice = totalPrice.add(sellingPrice.multiply(BigDecimal.valueOf(cartItem.getGoodsCount())));
        }
        order.setTotalPrice(totalPrice);
        order.setExtraInfo("");
        if(orderMapper.insert(order) != 1){
            throw new DataBaseErrorException();
        }
        for(ShoppingCartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(cartItem, orderItem);
            orderItem.setGoodsCoverImg(goodsMap.get(cartItem.getGoodsId()).getGoodsCoverImg());
            orderItem.setSellingPrice(goodsMap.get(cartItem.getGoodsId()).getSellingPrice());
            orderItem.setOrderId(order.getOrderId());
            if(orderItemMapper.insert(orderItem) != 1){
                throw new DataBaseErrorException();
            }
        }
        return orderNo;
    }

    @Override
    public PageResult<Order> getOrdersPage(Integer pageNum, Integer pageSize, String orderNo, Integer orderStatus) {
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Order.TableAttributes.USER_ID, UserContextUtil.getUserId());
        if(orderNo != null && !orderNo.isEmpty()) {
            queryWrapper.eq(Order.TableAttributes.ORDER_NO, orderNo);
        }
        if(orderStatus != null) {
            queryWrapper.eq(Order.TableAttributes.ORDER_STATUS, orderStatus);
        }
        int total = orderMapper.selectCount(queryWrapper);
        PageMethod.startPage(pageNum, pageSize);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        return new PageResult<>(orders, total, pageSize, pageNum);
    }

    @Override
    public void updateOrderInfo(Order order) {
        Order oldOrder = orderMapper.selectById(order.getOrderId());
        if(oldOrder.getOrderStatus() >= 3){
            throw new OrderStatusException();
        }
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkDone(List<Long> ids) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Order.TableAttributes.ORDER_ID, ids);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        if(orders.isEmpty()){
            throw new DataNotExistException();
        }
        for(Order order : orders) {
            if(order.getIsDeleted() || order.getOrderStatus() != 1) {
                throw new OrderStatusException();
            }
            order.setUpdateTime(new Date());
            order.setOrderStatus(Order.OrderStatus.ORDER_PACKAGED);
            orderMapper.updateById(order);
        }
    }

    @Override
    public void checkOut(List<Long> ids) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Order.TableAttributes.ORDER_ID, ids);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        if(orders.isEmpty()){
            throw new DataNotExistException();
        }
        for(Order order : orders) {
            if(order.getIsDeleted() || order.getOrderStatus() != 1) {
                throw new OrderStatusException();
            }
            order.setUpdateTime(new Date());
            order.setOrderStatus(Order.OrderStatus.ORDER_EXPRESS);
            orderMapper.updateById(order);
        }
    }

    @Override
    public void closeOrder(List<Long> ids) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(Order.TableAttributes.ORDER_ID, ids);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        if(orders.isEmpty()){
            throw new DataNotExistException();
        }
        for(Order order : orders) {
            if(order.getIsDeleted() || order.getOrderStatus() != 1) {
                throw new OrderStatusException();
            }
            order.setUpdateTime(new Date());
            order.setOrderStatus(Order.OrderStatus.ORDER_CLOSED_BY_JUDGE);
            orderMapper.updateById(order);
        }
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            throw new OrderNotExistException();
        }
        return orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq(Order.TableAttributes.ORDER_ID, orderId));
    }

    private Order getOrderByNoAndCheckUid(String orderNo){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Order.TableAttributes.ORDER_NO, orderNo);
        Order order = orderMapper.selectOne(queryWrapper);
        if(order == null){
            throw new OrderNotExistException();
        }
        if(!order.getUserId().equals(UserContextUtil.getUserId())){
            throw new AccessDeniedException();
        }
        return order;
    }

}
