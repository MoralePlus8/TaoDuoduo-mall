package ltd.common.cloud.taoduoduo.exception;

public class UserNotExistException extends Exception{
    public UserNotExistException() {
        super("用户数据不存在");
    }
}
