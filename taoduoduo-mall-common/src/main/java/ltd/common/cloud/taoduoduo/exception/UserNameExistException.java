package ltd.common.cloud.taoduoduo.exception;

public class UserNameExistException extends Exception{
    public UserNameExistException() {
        super("用户名已存在");
    }
}
