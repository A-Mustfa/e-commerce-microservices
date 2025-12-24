package commons.utils.Exceptions;

public class OrderCannotBeCanceledException extends RuntimeException {
    public OrderCannotBeCanceledException(String msg) {
        super(msg);
    }
}
