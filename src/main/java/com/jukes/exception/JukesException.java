package com.jukes.exception;

public class JukesException extends RuntimeException {

    public JukesException() {
        super();
    }

    public JukesException(Throwable exception) {
        super(exception);
    }

    public JukesException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public JukesException(String message, Throwable exception) {
        super(message, exception);
    }

}
