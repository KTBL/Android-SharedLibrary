package de.ktbl.android.sharedlibrary;

import androidx.annotation.RequiresApi;

/**
 * Signals that the application is in a state which it should never have reached.
 * In production state and if possible it should be tried to get back to some kind of default-state.
 */
public class IllegalAppStateException extends RuntimeException {
    /**
     * @see RuntimeException#RuntimeException()
     */
    public IllegalAppStateException() {
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public IllegalAppStateException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public IllegalAppStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public IllegalAppStateException(Throwable cause) {
        super(cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable, boolean, boolean)
     */
    @RequiresApi(api = 24)
    public IllegalAppStateException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
