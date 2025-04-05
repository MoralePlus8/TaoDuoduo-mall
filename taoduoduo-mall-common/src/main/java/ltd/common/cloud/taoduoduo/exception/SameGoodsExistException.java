package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class SameGoodsExistException extends RuntimeException {
    public SameGoodsExistException() {
        super(ServiceResultEnum.SAME_GOODS_EXIST.getResult());
    }
}
