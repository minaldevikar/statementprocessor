package com.customer.statementfileprocessor.util;

import com.customer.statementfileprocessor.exception.ExceptionHandler;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    static <T, R, E extends Exception> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new ExceptionHandler(e);
            }
        };
    }

    R apply(T t) throws E;
}