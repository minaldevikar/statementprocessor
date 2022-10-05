package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.bean.StatementFileInput;

import java.io.InputStream;

public interface FileProcessor {
    StatementFileInput process(InputStream inputStream);
}
