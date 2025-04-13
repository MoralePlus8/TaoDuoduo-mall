package ltd.common.cloud.taoduoduo.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {
    public LoginException() {
        super("用户名或密码错误");
    }
}
