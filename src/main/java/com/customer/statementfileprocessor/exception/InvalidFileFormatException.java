package com.customer.statementfileprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFileFormatException extends RuntimeException{

    public InvalidFileFormatException(String msg) {
        super(msg);
    }

    public InvalidFileFormatException() {
        super("Invalid file");
    }
}
