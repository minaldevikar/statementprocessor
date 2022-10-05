package com.customer.statementfileprocessor.service;

import com.customer.statementfileprocessor.bean.StatementFileInput;
import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.exception.ExceptionHandler;
import com.customer.statementfileprocessor.processor.FileProcessorFactory;
import com.customer.statementfileprocessor.util.FileType;
import com.customer.statementfileprocessor.util.RecordValidator;
import com.customer.statementfileprocessor.util.ThrowingFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class StatementFileProcessorService {

    public StatementFileOutput execute(MultipartFile file) {

        StatementFileInput input = Optional.of(file)
                .map(MultipartFile::getContentType)
                .map(FileType::get)
                .map(FileProcessorFactory::getFileProcessor)
                .map(ThrowingFunction.unchecked(fileProcessor -> fileProcessor.process(file.getInputStream())))
                .orElseThrow(() -> new ExceptionHandler());

        StatementFileOutput output = new StatementFileOutput();
        output.setResult(RecordValidator.validate(input));
        return output;
    }

}
