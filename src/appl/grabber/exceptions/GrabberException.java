package appl.grabber.exceptions;

/**
 * Created by ca on 16/07/16.
 */
public class GrabberException extends Exception {
    public GrabberException() {
        super();
    }

    public GrabberException(String message) {
        super(message);
    }

    public GrabberException(String message, Throwable cause) {
        super(message, cause);
    }

    public GrabberException(Throwable cause) {
        super(cause);
    }

    protected GrabberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
