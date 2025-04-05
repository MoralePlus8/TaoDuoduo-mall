package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class ParamErrorException extends RuntimeException {
    public ParamErrorException() {
        super(ServiceResultEnum.PARAM_ERROR.getResult());
    }
}
