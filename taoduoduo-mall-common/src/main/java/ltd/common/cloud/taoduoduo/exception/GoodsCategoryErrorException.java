package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class GoodsCategoryErrorException extends RuntimeException {
    public GoodsCategoryErrorException() {
        super(ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult());
    }
}
