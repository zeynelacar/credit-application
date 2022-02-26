package com.project.creditmangement.services;


import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Result;
import com.project.creditmangement.model.Score;
import com.project.creditmangement.repository.ApplicantRepository;
import com.project.creditmangement.repository.ResultRepository;
import com.project.creditmangement.repository.ScoreRepository;
import com.project.creditmangement.service.ApplicantService;
import com.project.creditmangement.service.ScoreService;
import com.project.creditmangement.service.implementations.ResultServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

     @Mock
     private ResultRepository resultRepository;

     @Mock
     private ApplicantRepository applicantRepository;

     @Mock
     private ScoreRepository scoreRepository;

     @Mock
     private ScoreService scoreService;

     @Mock
     private ApplicantService applicantService;

     @InjectMocks
     private ResultServiceImpl resultService;

     @Test
     void getAllResultsTest() {
         List<Result> expectedResults = Arrays.asList(
                 new Result(1,"33333333333","Denied",null),
                 new Result(2,"33333333322","Approved",10000),
                 new Result(3,"33333333311","Approved",20000)
         );

         //stub-when
         when(resultRepository.findAll()).thenReturn(expectedResults);

         //then
         List<Result> allResults = resultService.getAllResults();

         Assert.assertEquals(expectedResults.size(),allResults.size());
         for(Result expected : expectedResults){
             Optional<Result> actual = allResults
                     .stream().filter(
                             result -> result.getId().equals(expected.getId())
                     ).findFirst();
             Assert.assertEquals(expected.getNationalNo(),actual.get().getNationalNo());
             Assert.assertEquals(expected.getApplicationResult(),actual.get().getApplicationResult());
             Assert.assertEquals(expected.getLimit(),actual.get().getLimit());
         }
     }

     @Test
    void calculateLimitTest() {

         Score resultScore = new Score(1,"11111111111",500);
         Applicant resultApplicant = new Applicant(1,"ahmet","veli","11111111111",6000,"05004003020");

         Integer score = resultScore.getCreditScore();
         Integer monthlyIncome = resultApplicant.getMonthlyIncome();

         Integer otherScore = 500;
         Integer otherMonthlyIncome = 6000;

         Integer limit = resultService.calculateLimit(score,monthlyIncome);
         Integer otherLimit = resultService.calculateLimit(otherScore,otherMonthlyIncome);
         Assert.assertEquals(limit,otherLimit);
     }

    Score score = new Score(1,"11111111111",500);
    Applicant applicant = new Applicant(1,"ahmet","veli","11111111111",6000,"05004003020");

     @Test
    void createResultTest() {

         Score resultScore = new Score(1,"11111111111",500);
         Applicant resultApplicant = new Applicant(1,"ahmet","veli","11111111111",6000,"05004003020");

         doReturn(resultScore).when(scoreService).getScore("11111111111");

         String message = "Result of your application:Approved   Your credit limit is:20000";

         String result = resultService.createResult(resultApplicant.getNationalNo(),resultApplicant.getMonthlyIncome());

         Assert.assertEquals(message,result);

     }




}
