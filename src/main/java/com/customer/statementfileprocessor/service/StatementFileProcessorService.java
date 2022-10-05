package com.customer.statementfileprocessor.service;

import com.customer.statementfileprocessor.bean.Record;
import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.entity.RecordEntity;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.exception.StatementProcessorExceptionHandler;
import com.customer.statementfileprocessor.processor.FileProcessorFactory;
import com.customer.statementfileprocessor.repository.CustomerRecordRepo;
import com.customer.statementfileprocessor.util.FileType;
import com.customer.statementfileprocessor.util.RecordValidator;
import com.customer.statementfileprocessor.util.ThrowingFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StatementFileProcessorService {

    @Autowired
    private CustomerRecordRepo customerRecordRepo;
    public StatementFileOutput execute(MultipartFile file) {

        StatementFileInput input = Optional.of(file)
                .map(MultipartFile::getContentType)
                .map(FileType::get)
                .map(FileProcessorFactory::getFileProcessor)
                .map(ThrowingFunction.unchecked(fileProcessor -> fileProcessor.process(file.getInputStream())))
                .orElseThrow(() -> new InvalidFileFormatException("Can't read the data"));

        mapToRecordEntity(input);
        StatementFileOutput output = new StatementFileOutput();
        output.setResult(RecordValidator.validate(input));
        return output;
    }

    private void mapToRecordEntity(StatementFileInput statementFileInput){
         List<Record> records = statementFileInput.getRecordInputList()
                .parallelStream()
                .filter(record -> isReferenceUnique(statementFileInput, record) || isEndBalanceCorrect(record))
                .collect(Collectors.toList());
         saveRecord(records);
    }

    public boolean isReferenceUnique(StatementFileInput input, Record record) {
        return Collections.frequency(input.getRecordInputList(), record) < 1;
    }
    public static boolean isEndBalanceCorrect(Record record) {
        return record.getStartBalance().add(record.getMutation()).equals(record.getEndBalance());
    }
    @Transactional
    private void saveRecord(List<Record> recordList) {
        try {
            for (Record record : recordList) {
                RecordEntity recordEntity = new RecordEntity(record.getReference(), record.getAccountNumber(), record.getStartBalance(), record.getMutation(),
                        record.getDescription(), record.getEndBalance());
                customerRecordRepo.save(recordEntity);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
