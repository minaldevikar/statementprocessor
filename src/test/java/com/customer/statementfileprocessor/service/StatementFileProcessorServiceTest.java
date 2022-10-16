package com.customer.statementfileprocessor.service;

import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.entity.RecordEntity;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.repository.CustomerRecordRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StatementFileProcessorServiceTest {

    @Mock
    MultipartFile multipartFile;
    @Mock
    private CustomerRecordRepo cust;
    @InjectMocks
    private StatementFileProcessorService service;

    @Before
    public void init() {
        when(multipartFile.getContentType()).thenReturn("text/csv");
    }

    @Test
    public void executeSuccess() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/records.csv"));
        StatementFileOutput output = service.executeStatementProcessorRequest(multipartFile);
        assertEquals(3, output.getResult().size());
    }

    @Test(expected = InvalidFileFormatException.class)
    public void executeFailure() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/invalid.csv"));
        service.executeStatementProcessorRequest(multipartFile);
    }

    private List<RecordEntity> getData() {
        List<RecordEntity> entities = new ArrayList<>();
        RecordEntity record1 = new RecordEntity();
        record1.setReference(Long.parseLong("154270"));
        record1.setAccountNumber("NL56RABO0149876948");
        record1.setDescription("Candy for Peter de Vries");
        record1.setStartBalance(new BigDecimal("5429"));
        record1.setMutation(new BigDecimal("-939"));
        record1.setEndBalance(new BigDecimal("6368"));
        entities.add(record1);
        return entities;
    }
}
