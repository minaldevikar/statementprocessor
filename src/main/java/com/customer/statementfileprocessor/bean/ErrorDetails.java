package com.customer.statementfileprocessor.bean;

import java.util.Date;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
