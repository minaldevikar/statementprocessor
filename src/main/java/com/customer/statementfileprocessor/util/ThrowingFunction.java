package com.customer.statementfileprocessor.util;

import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.exception.StatementProcessorExceptionHandler;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    static <T, R, E extends Exception> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new InvalidFileFormatException("From throwing function"); //need to chnge error msg
            }
        };
    }

    R apply(T t) throws E;
}