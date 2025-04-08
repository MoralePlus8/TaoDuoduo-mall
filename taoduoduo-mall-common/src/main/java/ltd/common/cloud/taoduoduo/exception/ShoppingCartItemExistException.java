package ltd.common.cloud.taoduoduo.exception;


import ltd.common.cloud.taoduoduo.enums.ServiceResultEnum;

public class ShoppingCartItemExistException extends RuntimeException {
    public ShoppingCartItemExistException() {
        super(ServiceResultEnum.SHOPPING_CART_ITEM_EXIST_ERROR.getResult());
    }
}
