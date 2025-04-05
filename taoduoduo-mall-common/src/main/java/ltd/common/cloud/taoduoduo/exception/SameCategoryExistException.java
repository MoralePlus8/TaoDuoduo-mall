package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class SameCategoryExistException extends RuntimeException {
    public SameCategoryExistException() {
        super(ServiceResultEnum.SAME_CATEGORY_EXIST.getResult());
    }
}
