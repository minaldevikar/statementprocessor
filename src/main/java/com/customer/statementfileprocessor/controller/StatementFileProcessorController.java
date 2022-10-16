package com.customer.statementfileprocessor.controller;

import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.service.StatementFileProcessorService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("processor/api/v1")
@AllArgsConstructor
public class StatementFileProcessorController {

    private static final Logger logger = LoggerFactory.getLogger(StatementFileProcessorController.class);
    private StatementFileProcessorService statementFileProcessorService;

    @PostMapping("/upload")
    public ResponseEntity<StatementFileOutput> handleStatementProcessRequest(@NonNull @RequestParam("file") MultipartFile file) {
        logger.info("Inside handleStatementProcessRequest");
        return ResponseEntity.ok(statementFileProcessorService.executeStatementProcessorRequest(file));
    }

}
