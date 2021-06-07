package com.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.dto.NamePostcodePair;
import com.restapi.dto.NamesAndPostcodesRequest;
import com.restapi.jpa.NamesAndPostcodeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoController.class)
public class NamesAndPostcodesEndpointTests {

    public static final String NAMES_AND_POSTCODES_URI = "/names-and-postcodes";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private NamesAndPostcodeRepository namesAndPostcodeRepository;

    @Test
    public void withValidNamesAndPostcodesRequest_NamesAndPostcodesIsOk() throws Exception {
        NamesAndPostcodesRequest request = new NamesAndPostcodesRequest();
        List<NamePostcodePair> data = new ArrayList<>();
        data.add(new NamePostcodePair("some_name", "6000"));
        data.add(new NamePostcodePair("some_other_name", "6001"));
        request.setNamesAndPostcodes(data);

        this.mockMvc.perform(post(NAMES_AND_POSTCODES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(namesAndPostcodeRepository, Mockito.times(1)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void withInvalidPostcode_NamesAndPostcodesBadRequest() throws Exception {
        NamesAndPostcodesRequest request = new NamesAndPostcodesRequest();
        List<NamePostcodePair> data = new ArrayList<>();
        // Obviously INVALID_POST_CODE isn't a number
        data.add(new NamePostcodePair("some_name", "INVALID_POST_CODE"));
        request.setNamesAndPostcodes(data);

        this.mockMvc.perform(post(NAMES_AND_POSTCODES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        Mockito.verify(namesAndPostcodeRepository, Mockito.times(0)).saveAll(Mockito.any(List.class));
    }

    @Test
    public void withNoName_NamesAndPostcodesBadRequest() throws Exception {
        NamesAndPostcodesRequest request = new NamesAndPostcodesRequest();
        List<NamePostcodePair> data = new ArrayList<>();
        // Name is mandatory!
        data.add(new NamePostcodePair("", "6000"));
        request.setNamesAndPostcodes(data);

        this.mockMvc.perform(post(NAMES_AND_POSTCODES_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        Mockito.verify(namesAndPostcodeRepository, Mockito.times(0)).saveAll(Mockito.any(List.class));
    }

}
