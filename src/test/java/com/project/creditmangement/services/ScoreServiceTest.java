package com.project.creditmangement.services;

import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.entity.Score;
import com.project.creditmangement.repository.ScoreRepository;
import com.project.creditmangement.service.ApplicantService;
import com.project.creditmangement.service.implementations.ScoreServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private ApplicantService applicantService;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    Applicant applicant = new Applicant(1,"ahmet","veli","11111111111",1000,"05004003020");

    @Test
    void getScoreTest() {

        //init
        Score expectedScore = new Score(1,"11111111111",1000);

        //when
        doReturn(expectedScore).when(scoreRepository).findByNationalNo("11111111111");

        //then
        Score actualScore = scoreService.getScore(expectedScore.getNationalNo());

        //valid
        assertEquals(expectedScore,actualScore);
    }

    @Test
    void addScoreTest(){
        //init
        Score expectedScore = new Score(1,"11111111111",1000);
        //stub
        when(scoreRepository.save(expectedScore)).thenReturn(expectedScore);
        //then
        scoreService.addScore(expectedScore);
        //valid
        verify(scoreRepository,times(1)).save(expectedScore);

    }

    @Test
    void testScore(){

        boolean control = scoreService.createScore("11111111111");

        Assert.assertTrue(control);
    }

}
