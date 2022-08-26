package com.project.creditmangement.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.creditmangement.controller.ResultController;
import com.project.creditmangement.exception.handler.GenericHandler;
import com.project.creditmangement.model.dto.ApplicantDTO;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.entity.Result;
import com.project.creditmangement.model.entity.Score;
import com.project.creditmangement.model.mapper.ApplicantMapper;
import com.project.creditmangement.service.implementations.ResultServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class ResultControllerTest {

    private MockMvc mvc;

    @Mock
    private ResultServiceImpl resultService;

    @InjectMocks
    private ResultController resultController;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());
        // Standalone approach
        mvc = MockMvcBuilders.standaloneSetup(resultController)
                .setControllerAdvice(new GenericHandler())
                .build();
    }

    @Test
    void getAllResults() throws Exception{

        // init test values / given
        List<Result> expectedResults = getTestResults();

        // stub - when
        Mockito.when(resultService.getAllResults()).thenReturn(expectedResults);

        MockHttpServletResponse response = mvc.perform(get("/application/results")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Result> actualResults = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Result>>() {
        });
        assertEquals(expectedResults.size(), actualResults.size());

    }

    @Test
    void searchResult() throws Exception{

        // init test values
        List<Result> expectedResults = getTestResults();

        // stub - given
        Mockito.when(resultService.getResult("11111111111")).thenReturn(expectedResults.get(0));

        MockHttpServletResponse response = mvc.perform(get("/application/search?nationalId=11111111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Result actualResult = new ObjectMapper().readValue(response.getContentAsString(), Result.class);
        Assert.assertEquals(expectedResults.get(0), actualResult);

    }

    @Test
    void makeApplication() throws Exception{

        // init test values
        List<Result> expectedResults = getTestResults();
        ApplicantDTO applicant = new ApplicantDTO();
        applicant.setName("ahmet");
        applicant.setSurname("veli");
        applicant.setNationalNo("11111111111");
        applicant.setMonthlyIncome(1000);
        applicant.setPhone("05004003020");
        Score score = new Score(1,"11111111111",1200);
        String expectedResult = "Result of your application:Approved   Your credit limit is:4000";

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedResultJsonStr = ow.writeValueAsString(applicant);
        Mockito.doReturn(expectedResult).when(resultService).makeApplication(ApplicantMapper.toEntity(applicant));

        MockHttpServletResponse response = mvc.perform(post("/application/apply")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedResultJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals(expectedResult, actualResponseStr);

    }

    @Test
    void deleteResult() throws Exception{

        // stub - given
        Mockito.when(resultService.deleteResult(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/application/delete?nationalId=11111111111")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals("true", actualResponseStr);

    }



    List<Result> getTestResults() {

        List<Result> results = new ArrayList<>();
        results.add(new Result("11111111111","Denied",null));
        results.add(new Result("22222222222","Approved",10000));
        results.add(new Result("33333333333","Approved",50000));
        return results;

    }




}
