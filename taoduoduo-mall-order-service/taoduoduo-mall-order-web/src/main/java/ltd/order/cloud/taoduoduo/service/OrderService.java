package ltd.order.cloud.taoduoduo.service;

import ltd.common.cloud.taoduoduo.dto.PageResult;
import ltd.common.cloud.taoduoduo.entity.Address;
import ltd.common.cloud.taoduoduo.entity.Order;
import ltd.common.cloud.taoduoduo.entity.OrderItem;

import java.util.List;

public interface OrderService {

    Order getOrderDetailByOrderId(Long orderId);

    Order getOrderDetailByOrderNo(String orderNo);

    PageResult<Order> getMyOrders(Integer pageNum, Integer orderStatus);

    void cancelOrder(String orderNo);

    void finishOrder(String orderNo);

    void paySuccess(String orderNo, int payType);

    String saveOrder(Long addressId, List<Long> cartItemIds);

    PageResult<Order> getOrdersPage(Integer pageNum, Integer pageSize, String orderNo, Integer orderStatus);

    void updateOrderInfo(Order order);

    void checkDone(List<Long> ids);

    void checkOut(List<Long> ids);

    void closeOrder(List<Long> ids);

    List<OrderItem> getOrderItems(Long orderId);
}
