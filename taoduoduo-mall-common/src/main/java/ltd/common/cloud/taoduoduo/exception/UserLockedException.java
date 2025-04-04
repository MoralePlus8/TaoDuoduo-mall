package ltd.common.cloud.taoduoduo.exception;

public class UserLockedException extends Exception{
    public UserLockedException() {
        super("用户已锁定");
    }
}
