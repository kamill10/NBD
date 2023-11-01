package p.lodz.Exceptions;

public class InvalidPurchaseException extends RuntimeException {
    public InvalidPurchaseException(String msg) {
        super(msg);
    }
}
