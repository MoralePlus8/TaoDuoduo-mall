package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class GoodsPutDownException extends RuntimeException {
    public GoodsPutDownException() {
        super(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
    }
}
