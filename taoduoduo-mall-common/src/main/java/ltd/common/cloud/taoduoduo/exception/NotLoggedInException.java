package ltd.common.cloud.taoduoduo.exception;

public class NotLoggedInException extends Exception{
    public NotLoggedInException() {
        super("用户未登录");
    }
}
