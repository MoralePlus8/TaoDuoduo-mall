package ltd.common.cloud.taoduoduo.exception;

public class WrongPasswordException extends Exception{
    public WrongPasswordException() {
        super("密码错误");
    }
}
