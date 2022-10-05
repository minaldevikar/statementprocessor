package com.customer.statementfileprocessor.controller;

import com.customer.statementfileprocessor.exception.InvalidFileFormatException;
import com.customer.statementfileprocessor.service.StatementFileProcessorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatementFileProcessorControllerTest {

    private InputStream is;
    private MockMvc mockMvc;

    @Mock
    private StatementFileProcessorService statementFileProcessorService;

    @Spy
    @InjectMocks
    private StatementFileProcessorController controller = new StatementFileProcessorController(statementFileProcessorService);

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        is = controller.getClass().getClassLoader().getResourceAsStream("/records.xml");
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/processor/api/v1/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void testUploadFileHttp404() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile).contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(404)).andReturn();
        assertEquals(404, result.getResponse().getStatus());
    }

}
