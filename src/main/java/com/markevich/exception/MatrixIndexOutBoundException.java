package com.markevich.exception;

public class MatrixIndexOutBoundException extends Exception {
    public MatrixIndexOutBoundException() {
        super();
    }

    public MatrixIndexOutBoundException(String message) {
        super(message);
    }

    public MatrixIndexOutBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixIndexOutBoundException(Throwable cause) {
        super(cause);
    }
}
