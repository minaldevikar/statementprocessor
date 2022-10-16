package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.bean.XmlRecord;
import com.customer.statementfileprocessor.bean.XmlRecords;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class XmlProcessor implements FileProcessor {
    @Override
    public StatementFileInput process(InputStream inputStream) {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new ParameterNamesModule());
        xmlMapper.registerModule(new Jdk8Module());
        xmlMapper.registerModule(new JavaTimeModule());

        XmlRecords xmlRecords;
        try {
            xmlRecords = xmlMapper.readValue(inputStream, XmlRecords.class);
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidFileFormatException("Invalid xml file");
        }
        return convert(xmlRecords);
    }

    private StatementFileInput convert(XmlRecords xmlRecords) {

        StatementFileInput statementFileInput = new StatementFileInput();
        statementFileInput.setRecordInputList(xmlRecords.getXmlRecordList()
                .stream()
                .map(this::mapXmlRecord)
                .toList());
        return statementFileInput;
    }

    private Record mapXmlRecord(XmlRecord xmlRecord) {
        Record recordInput;
        try {
            recordInput = new Record(Long.parseLong(xmlRecord.getReference()), xmlRecord.getAccountNumber(), xmlRecord.getDescription(),
                    new BigDecimal(xmlRecord.getStartBalance()), new BigDecimal(xmlRecord.getMutation()), new BigDecimal(xmlRecord.getEndBalance()));
        } catch (NumberFormatException nfe) {
            throw new InvalidFileFormatException("Can't map csv data. Please check the input");
        }
        return recordInput;
    }
}
