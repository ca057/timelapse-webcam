package appl.timelapse.exceptions;

/**
 * Created by ca on 16/07/16.
 */
public class TimelapseException extends Exception {
    public TimelapseException() {
        super();
    }

    public TimelapseException(String message) {
        super(message);
    }

    public TimelapseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimelapseException(Throwable cause) {
        super(cause);
    }

    protected TimelapseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
