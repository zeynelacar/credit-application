package com.project.creditmanagement.services;

import com.foreach.common.test.MockedLoader;
import com.project.creditmanagement.cache.CacheManager;
import com.project.creditmanagement.exception.NotFoundException;
import com.project.creditmanagement.model.entity.Applicant;
import com.project.creditmanagement.repository.ApplicantRepository;
import com.project.creditmanagement.service.implementations.ApplicantServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApplicantServiceTest.ApplicationTestConfig.class,loader = MockedLoader.class)
public class ApplicantServiceTest  {

    @Mock
    private ApplicantRepository applicantRepository;

    @InjectMocks
    private ApplicantServiceImpl applicantService;

    @Autowired
    @InjectMocks
    private CacheManager cacheManager;

    @Autowired
    public void createCache(){
        List<Applicant> allApplicants = applicantRepository.findAll();
        if(!allApplicants.isEmpty())
            cacheManager.cacheAll("Applicants",allApplicants);
    }


    @Test
    void getAllApplicants(){
        List<Applicant> expectedApplicants = Arrays.asList(
                new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020"),
                new Applicant(2,"ahmet","sungur","11111111122",5000,"05014003020"),
                new Applicant(3,"polat","alemdar","11111122233",1800,"05024003020"),
                new Applicant(4,"ali","candan","77711111111",20000,"05034003020")
        );

        //stub-when
        Mockito.when(applicantRepository.findAll()).thenReturn(expectedApplicants);

        //then
        List<Applicant> allApplicants = applicantService.getAllApplicants();

        Assert.assertEquals(expectedApplicants.size(),allApplicants.size());
        for(Applicant expected : expectedApplicants){
            Optional<Applicant> actual = allApplicants
                    .stream().filter(
                            applicant -> applicant.getId().equals(expected.getId())
                    ).findFirst();
            Assert.assertEquals(expected.getName(),actual.get().getName());
            Assert.assertEquals(expected.getSurname(),actual.get().getSurname());
            Assert.assertEquals(expected.getNationalNo(),actual.get().getNationalNo());
            Assert.assertEquals(expected.getMonthlyIncome(),actual.get().getMonthlyIncome());
            Assert.assertEquals(expected.getPhone(),actual.get().getPhone());
        }
    }


    @Test
    void getApplicantTest(){


        //init
        Applicant expectedApplicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");

        //stub
        Mockito.doReturn(expectedApplicant).when(applicantRepository).findByNationalNo(expectedApplicant.getNationalNo());

        //then
        Applicant actualApplicant = applicantService.getApplicant(expectedApplicant.getNationalNo());

        //valid
        assertEquals(expectedApplicant,actualApplicant);

    }

    @Test
    void getApplicant_not_found() {

        // Only then step, mockito says stubbing is unnecessary.
        try {
            //fail
            Applicant actualApplicant = applicantService.getApplicant("11111111111");
        } catch (NotFoundException expected) {
            //match exceptions
            assertEquals("Applicant not found!", expected.getMessage());
        }
    }

    @Test
    void addApplicant(){
        //init
        Applicant expectedApplicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");
        //stub
        //when(applicantRepository.save(expectedApplicant)).thenReturn(expectedApplicant);
        //then
        applicantService.addApplicant(expectedApplicant);
        //valid
        //verify(applicantRepository,times(1)).save(expectedApplicant);

    }


    @Test
    void updateApplicant() {
        Applicant expectedApplicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");
        //stub
        //when(applicantRepository.save(expectedApplicant)).thenReturn(expectedApplicant);
        //then
        applicantService.updateApplicant(expectedApplicant);
        //valid
        //verify(applicantRepository,times(1)).save(expectedApplicant);
    }

    @Test
    void deleteApplicant() {

        Applicant expectedApplicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");

        //when(applicantRepository.save(expectedApplicant)).thenReturn(expectedApplicant);
        //doNothing().when(applicantRepository).delete(expectedApplicant);

        applicantService.addApplicant(expectedApplicant);
        boolean status = applicantService.deleteApplicant(expectedApplicant.getNationalNo());

        Assert.assertTrue(status);

    }

    @Test
    void questionApplicant() {

        Applicant expectedApplicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");

        boolean preexistence = false;

        //doReturn(preexistence).when(applicantRepository).existsByNationalNo(expectedApplicant.getNationalNo());

        boolean existence = applicantService.questionApplicant(expectedApplicant.getNationalNo());

        Assert.assertEquals(existence,preexistence);

    }

    @Configuration
    @ComponentScan(basePackages = {"com.project.creditmanagement"},
            useDefaultFilters = false,
            includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value= CacheManager.class)})
    protected static class ApplicationTestConfig{}

}
