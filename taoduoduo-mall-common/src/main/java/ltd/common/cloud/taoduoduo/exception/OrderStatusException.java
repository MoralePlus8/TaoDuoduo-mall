package ltd.common.cloud.taoduoduo.exception;

public class OrderStatusException extends RuntimeException {
    public OrderStatusException() {
        super("订单状态异常");
    }
}
