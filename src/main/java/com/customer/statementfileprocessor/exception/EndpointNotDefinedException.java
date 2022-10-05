package com.customer.statementfileprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 8/22/2022.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EndpointNotDefinedException extends RuntimeException {

    public EndpointNotDefinedException(String msg) {
        super(msg);
    }

    public EndpointNotDefinedException() {
        super("Api Endpoint is not defined");
    }
}
