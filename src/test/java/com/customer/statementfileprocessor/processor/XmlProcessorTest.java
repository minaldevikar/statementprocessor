package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlProcessorTest {

    @Test
    public void processSuccess() {
        FileProcessor xmlProcessor = new XmlProcessor();
        StatementFileInput input = xmlProcessor.process(getClass().getResourceAsStream("/records.xml"));

        assertEquals(10, input.getRecordInputList().size());
        assertEquals(Long.valueOf("187997"), input.getRecordInputList().get(0).getReference());
        assertEquals("NL91RABO0315273637", input.getRecordInputList().get(0).getAccountNumber());
        assertEquals("Clothes for Rik King", input.getRecordInputList().get(0).getDescription());
        assertEquals(BigDecimal.valueOf(57.6), input.getRecordInputList().get(0).getStartBalance());
        assertEquals(BigDecimal.valueOf(-32.98), input.getRecordInputList().get(0).getMutation());
        assertEquals(BigDecimal.valueOf(24.62), input.getRecordInputList().get(0).getEndBalance());
    }

    @Test(expected = InvalidFileFormatException.class)
    public void processFailureWrongData() {
        FileProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.process(getClass().getResourceAsStream("/wrong_records.xml"));
    }

    @Test(expected = InvalidFileFormatException.class)
    public void processFailure() {
        FileProcessor xmlProcessor = new XmlProcessor();
        xmlProcessor.process(getClass().getResourceAsStream("/invalid.xml"));
    }

}
