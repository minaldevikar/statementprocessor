package com.customer.statementfileprocessor.processor;

import com.customer.statementfileprocessor.bean.CsvRecord;
import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class CsvProcessor implements FileProcessor {
    @Override
    public StatementFileInput process(InputStream inputStream) {
        List<CsvRecord> csvRecords;
        try {
            csvRecords = new CsvToBeanBuilder<CsvRecord>(new BufferedReader(new InputStreamReader(inputStream)))
                    .withOrderedResults(false)
                    .withType(CsvRecord.class)
                    .build()
                    .parse();
        } catch (NumberFormatException | IllegalStateException | NullPointerException exception) {
            throw new InvalidFileFormatException("Invalid CSV File");
        }
        return convert(csvRecords);
    }

    private StatementFileInput convert(List<CsvRecord> csvStatementRecords) {

        StatementFileInput statementFileInput = new StatementFileInput();
        statementFileInput.setRecordInputList(csvStatementRecords
                .stream()
                .map(this::mapCsvRecord)
                .toList());
        return statementFileInput;
    }

    private Record mapCsvRecord(CsvRecord csvRecord) {
        Record recordInput;
        try {
            recordInput = new Record(Long.parseLong(csvRecord.getReference()),csvRecord.getAccountNumber(),csvRecord.getDescription(),
                    new BigDecimal(csvRecord.getStartBalance()),new BigDecimal(csvRecord.getMutation()),new BigDecimal(csvRecord.getEndBalance()));
        } catch (NumberFormatException nfe) {
            throw new InvalidFileFormatException("Can't map csv data. Please check the input");
        }
        return recordInput;
    }
}
