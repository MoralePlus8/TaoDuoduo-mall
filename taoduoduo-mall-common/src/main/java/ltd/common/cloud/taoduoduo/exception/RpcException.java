package ltd.common.cloud.taoduoduo.exception;

public class RpcException extends RuntimeException {
    public RpcException() {
        super("RPC调用异常");
    }
}
