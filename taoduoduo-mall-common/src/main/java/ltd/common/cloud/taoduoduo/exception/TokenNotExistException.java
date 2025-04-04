package ltd.common.cloud.taoduoduo.exception;

public class TokenNotExistException extends Exception{
    public TokenNotExistException() {
        super("Token不存在");
    }
}
