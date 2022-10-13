package com.customer.statementfileprocessor.service;

import com.customer.statementfileprocessor.bean.StatementFileOutput;
import com.customer.statementfileprocessor.exception.DataAlreadyExistsException;
import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.repository.CustomerRecordRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StatementFileProcessorServiceTest {

    @Mock
    MultipartFile multipartFile;
    @Mock
    private CustomerRecordRepo cust;
    private StatementFileProcessorService service = new StatementFileProcessorService();

    @Before
    public void init() {
        when(multipartFile.getContentType()).thenReturn("text/csv");
    }

    //@Test
    public void executeSuccess() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/records.csv"));
        when(cust.saveAll(Mockito.anyList())).thenReturn(Mockito.any());
        StatementFileOutput output = service.executeStatementProcessorRequest(multipartFile);
        assertEquals(3, output.getResult().size());
    }

    @Test(expected = InvalidFileFormatException.class)
    public void executeFailure() throws IOException {
        when(multipartFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/invalid.csv"));
        service.executeStatementProcessorRequest(multipartFile);
    }
}
