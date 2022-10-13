package com.customer.statementfileprocessor.service;

import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.entity.RecordEntity;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.processor.FileProcessorFactory;
import com.customer.statementfileprocessor.repository.CustomerRecordRepo;
import com.customer.statementfileprocessor.util.FileType;
import com.customer.statementfileprocessor.util.RecordValidator;
import com.customer.statementfileprocessor.util.ThrowingFunction;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.customer.statementfileprocessor.util.RecordValidator.isEndBalanceNotCorrect;
import static com.customer.statementfileprocessor.util.RecordValidator.isReferenceNotUnique;


@Service
//@AllArgsConstructor
public class StatementFileProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(StatementFileProcessorService.class);
    @Autowired
    private CustomerRecordRepo customerRecordRepo;
    public StatementFileOutput executeStatementProcessorRequest(MultipartFile file) {
        logger.info("Inside executeStatementProcessorRequest");
        StatementFileInput input = Optional.of(file)
                .map(MultipartFile::getContentType)
                .map(FileType::get)
                .map(FileProcessorFactory::getFileProcessor)
                .map(ThrowingFunction.unchecked(fileProcessor -> fileProcessor.process(file.getInputStream())))
                .orElseThrow(() -> new InvalidFileFormatException("Can't read the data"));

        mapToRecordEntityAndSave(input);
        StatementFileOutput output = new StatementFileOutput();
        output.setResult(RecordValidator.validate(input));
        logger.info("Exit from executeStatementProcessorRequest");
        return output;
    }

    private void mapToRecordEntityAndSave(StatementFileInput statementFileInput){
        logger.info("Inside mapToRecordEntityAndSave");
        List<RecordEntity> records = statementFileInput.getRecordInputList()
                .parallelStream()
                .filter(record -> !(isReferenceNotUnique(statementFileInput, record) || isEndBalanceNotCorrect(record)))
                .map(RecordValidator::presistValidResult)
                .collect(Collectors.toList());
       customerRecordRepo.saveAll(records);
    }
}
