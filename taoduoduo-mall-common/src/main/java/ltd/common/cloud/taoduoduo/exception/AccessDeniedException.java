package ltd.common.cloud.taoduoduo.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super("没有权限访问该资源");
    }
}
