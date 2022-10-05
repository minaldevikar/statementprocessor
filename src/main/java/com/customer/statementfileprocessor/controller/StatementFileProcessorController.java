package com.customer.statementfileprocessor.controller;

import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.exception.EndpointNotDefinedException;
import com.customer.statementfileprocessor.exception.StatementProcessorExceptionHandler;
import com.customer.statementfileprocessor.service.StatementFileProcessorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("processor/api/v1")
public class StatementFileProcessorController {

    private static final Logger logger = LoggerFactory.getLogger(StatementFileProcessorController.class);


    private StatementFileProcessorService statementFileProcessorService;
    @Autowired
    public StatementFileProcessorController(StatementFileProcessorService statementFileProcessorService) {
        this.statementFileProcessorService = statementFileProcessorService;
    }

    @PostMapping("/upload")
    public ResponseEntity<StatementFileOutput> handle(@NonNull @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(statementFileProcessorService.execute(file));
    }

    @GetMapping(value = {"/error", "/{v1}/*"})
    public ResponseEntity<?> endPointNotDefined() {
        logger.info("Inside /error");
        throw new EndpointNotDefinedException();
    }
}
