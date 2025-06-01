package ltd.common.cloud.taoduoduo.exception;

public class OrderNotExistException extends RuntimeException {
    public OrderNotExistException() {
        super("订单不存在");
    }
}
