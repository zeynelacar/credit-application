package com.project.creditmangement.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.creditmangement.controller.ApplicantController;
import com.project.creditmangement.exception.handler.GenericHandler;
import com.project.creditmangement.model.dto.ApplicantDTO;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.mapper.ApplicantMapper;
import com.project.creditmangement.service.implementations.ApplicantServiceImpl;
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
public class ApplicantControllerTest {

    private MockMvc mvc;

    @Mock
    private ApplicantServiceImpl applicantService;

    @InjectMocks
    private ApplicantController applicantController;


    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());
        // Standalone approach
        mvc = MockMvcBuilders.standaloneSetup(applicantController)
                .setControllerAdvice(new GenericHandler())
                .build();
    }


    @Test
    void getAllApplicants() throws Exception {
        // init test values / given
        List<Applicant> expectedApplicants = getTestApplicants();

        // stub - when
        Mockito.when(applicantService.getAllApplicants()).thenReturn(expectedApplicants);

        MockHttpServletResponse response = mvc.perform(get("/applicant/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Applicant> actualApplicants = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Applicant>>() {
        });
        assertEquals(expectedApplicants.size(), actualApplicants.size());

    }


    @Test
    void getApplicant() throws Exception {
        // init test values
        List<Applicant> expectedApplicants = getTestApplicants();

        // stub - given
        Mockito.when(applicantService.getApplicant("11111111111")).thenReturn(expectedApplicants.get(0));

        MockHttpServletResponse response = mvc.perform(get("/applicant/find?nationalId=11111111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Applicant actualApplicant = new ObjectMapper().readValue(response.getContentAsString(), Applicant.class);
        Assert.assertEquals(expectedApplicants.get(0), actualApplicant);

    }

    @Test
    void addApplicant() throws Exception {

        // init test values
        List<Applicant> expectedApplicants = getTestApplicants();
        ApplicantDTO expectedApplicant = new ApplicantDTO() ;//(5,"avni","silik","11111111199",1000,"05004003050");
        expectedApplicant.setName("avni");
        expectedApplicant.setSurname("silik");
        expectedApplicant.setNationalNo("11111111199");
        expectedApplicant.setMonthlyIncome(1000);
        expectedApplicant.setPhone("05004003050");


        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedApplicantJsonStr = ow.writeValueAsString((expectedApplicant));
        Mockito.doNothing().when(applicantService).addApplicant(ApplicantMapper.toEntity(expectedApplicant));

        MockHttpServletResponse response = mvc.perform(post("/applicant/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedApplicantJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(applicantService, Mockito.times(1)).addApplicant(any());

    }

    @Test
    void updateApplicant() throws Exception {

        List<Applicant> expectedApplicants = getTestApplicants();
        ApplicantDTO expectedApplicant = new ApplicantDTO() ;//(5,"avni","silik","11111111199",1000,"05004003050");
        expectedApplicant.setName("avni");
        expectedApplicant.setSurname("silik");
        expectedApplicant.setNationalNo("11111111199");
        expectedApplicant.setMonthlyIncome(1000);
        expectedApplicant.setPhone("05004003050");
        //expectedApplicants.add(expectedApplicant);

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedApplicantJsonStr = ow.writeValueAsString(expectedApplicant);
        Mockito.doReturn(ApplicantMapper.toEntity(expectedApplicant)).when(applicantService).updateApplicant(ApplicantMapper.toEntity(expectedApplicant));

        MockHttpServletResponse response = mvc.perform(put("/applicant/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedApplicantJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(applicantService, Mockito.times(1)).updateApplicant(any());

    }

    @Test
    void deleteApplicant() throws Exception {

        // stub - given
        Mockito.when(applicantService.deleteApplicant(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/applicant/delete?nationalId=11111111111")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals("true", actualResponseStr);

    }




    private List<Applicant> getTestApplicants() {

        List<Applicant> applicants = new ArrayList<>();
        applicants.add(new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020"));
        applicants.add(new Applicant(2,"ahmet","sungur","11111111122",5000,"05014003020"));
        applicants.add(new Applicant(3,"polat","alemdar","11111122233",1800,"05024003020"));
        applicants.add(new Applicant(4,"ali","candan","77711111111",20000,"05034003020"));
        return applicants;
    }


}
