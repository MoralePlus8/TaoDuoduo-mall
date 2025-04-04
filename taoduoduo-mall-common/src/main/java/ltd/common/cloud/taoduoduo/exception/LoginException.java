package ltd.common.cloud.taoduoduo.exception;

public class LoginException extends Exception{
    public LoginException() {
        super("用户名或密码错误");
    }
}
