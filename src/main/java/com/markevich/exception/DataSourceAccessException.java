package com.markevich.exception;

public class DataSourceAccessException extends Exception {
    public DataSourceAccessException() {
        super();
    }

    public DataSourceAccessException(String message) {
        super(message);
    }

    public DataSourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceAccessException(Throwable cause) {
        super(cause);
    }
}
