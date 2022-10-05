package com.customer.statementfileprocessor.exception;

public class ExceptionHandler extends RuntimeException{
    public ExceptionHandler() {
    }

    public ExceptionHandler(String message) {
        super(message);
    }

    public ExceptionHandler(Throwable throwable) {
        super(throwable);
    }

    public ExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
