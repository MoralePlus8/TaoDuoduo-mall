package ltd.common.cloud.taoduoduo.exception;

public class AddressNotExistException extends RuntimeException {
    public AddressNotExistException() {
        super("Address does not exist");
    }
}
