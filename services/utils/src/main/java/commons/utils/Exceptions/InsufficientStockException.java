package commons.utils.Exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String msg) {
        super(msg);
    }
}
