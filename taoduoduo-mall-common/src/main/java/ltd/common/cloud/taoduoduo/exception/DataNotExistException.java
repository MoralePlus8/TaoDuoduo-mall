package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class DataNotExistException extends RuntimeException {
    public DataNotExistException() {
        super(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }
}
