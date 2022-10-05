package com.customer.statementfileprocessor.exception;

import com.customer.statementfileprocessor.bean.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class StatementProcessorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class,Exception.class})
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,                                                            WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), 400, exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EndpointNotDefinedException.class)
    public ResponseEntity<ErrorDetails> handleEndpointNotDefinedException(Exception exception,
                                                                          WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), 400, exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorDetails> handleInvalidFileFormatException(Exception exception,
                                                                          WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), 400, exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
