package ltd.common.cloud.taoduoduo.exception;

import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class GoodsNotExistException extends RuntimeException {
  public GoodsNotExistException() {
    super(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
  }
}
