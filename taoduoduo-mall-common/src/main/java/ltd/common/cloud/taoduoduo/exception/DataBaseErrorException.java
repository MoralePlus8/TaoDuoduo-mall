package ltd.common.cloud.taoduoduo.exception;

public class DataBaseErrorException extends RuntimeException{
    public DataBaseErrorException() {
        super("数据库错误");
    }
}
