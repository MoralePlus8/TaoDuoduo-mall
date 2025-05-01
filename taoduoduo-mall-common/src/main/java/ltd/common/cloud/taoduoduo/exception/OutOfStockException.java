package ltd.common.cloud.taoduoduo.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException() {
        super("商品库存不足");
    }
}
