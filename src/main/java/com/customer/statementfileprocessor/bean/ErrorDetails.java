package com.customer.statementfileprocessor.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorDetails {
    private Date timestamp;
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, Integer statusCode, String message, String details) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

}
