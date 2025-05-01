package ltd.common.cloud.taoduoduo.exception;

public class IllegalCountException extends RuntimeException{
    public IllegalCountException() {
        super("数量不合法");
    }
}
