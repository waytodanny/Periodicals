package com.periodicals.exceptions;

/**
 * @author Daniel Volnitsky
 * <p>
 * Exception made for notifying about exceptions during some transaction
 */
public class TransactionException extends Throwable {
    public TransactionException() {
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
