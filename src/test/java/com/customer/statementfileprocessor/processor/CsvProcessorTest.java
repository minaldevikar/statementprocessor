package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvProcessorTest {

    @Test
    public void processSuccess() {
        FileProcessor csvProcessor = new CsvProcessor();
        StatementFileInput input = csvProcessor.process(getClass().getResourceAsStream("/records.csv"));

        assertEquals(10, input.getRecordInputList().size());
    }

    @Test(expected = InvalidFileFormatException.class)
    public void processFailureWrongData() {
        FileProcessor csvProcessor = new CsvProcessor();
        csvProcessor.process(getClass().getResourceAsStream("/wrong_records.csv"));
    }

    @Test(expected = InvalidFileFormatException.class)
    public void processFailureWrongFile() {
        FileProcessor csvProcessor = new CsvProcessor();
        csvProcessor.process(getClass().getResourceAsStream("/invalid.csv"));
    }
}
