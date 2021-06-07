package com.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.jpa.NamesAndPostcodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
public class NamesAndCharacterCountTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private NamesAndPostcodeRepository namesAndPostcodeRepository;

    @Test
    public void withValidParams_namesAndCharacterCountIsOk() throws Exception {
        this.mockMvc.perform(get("/names-and-character-count")
                .param("lower", "6000")
                .param("upper", "7000"))
                .andExpect(status().isOk());

        Mockito.verify(namesAndPostcodeRepository, Mockito.times(1)).findAllNamesForRange(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void withInValidParams_namesAndCharacterCountIsBadRequest() throws Exception {
        // i.e. lower higher than upper
        this.mockMvc.perform(get("/names-and-character-count")
                .param("lower", "7000")
                .param("upper", "6000"))
                .andExpect(status().isBadRequest());

        Mockito.verify(namesAndPostcodeRepository, Mockito.times(0)).findAllNamesForRange(Mockito.anyInt(), Mockito.anyInt());
    }
}
