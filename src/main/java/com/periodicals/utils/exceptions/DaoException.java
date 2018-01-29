package com.periodicals.utils.exceptions;

/**
 * @author Daniel Volnitsky
 * <p>
 * Exception made for Dao layer to cover Dao-level exceptions
 */
public class DaoException extends Exception {

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

