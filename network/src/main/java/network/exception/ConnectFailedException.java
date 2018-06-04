package network.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Boiling
 * Date: 2018-06-04
 * Time: 16:47
 */

public class ConnectFailedException extends RuntimeException {

    private static final long serialVersionUID = -2890742743547564900L;

    public ConnectFailedException() {
        super();
    }

    public ConnectFailedException(String message) {
        super(message);
    }

    public ConnectFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectFailedException(Throwable cause) {
        super(cause);
    }
}