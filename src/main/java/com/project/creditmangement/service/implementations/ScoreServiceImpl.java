package com.project.creditmangement.service.implementations;

import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Score;
import com.project.creditmangement.repository.ScoreRepository;
import com.project.creditmangement.service.ApplicantService;
import com.project.creditmangement.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    private final ApplicantService applicantService;



    @Override
    public Score getScore(String nationalId) {
        try{
            if(applicantService.questionApplicant(nationalId)){
                return scoreRepository.findByNationalNo(nationalId);
            }else{
                return createScore(nationalId);
            }
        }catch(Exception e){
            throw new RuntimeException("Score not found");
        }
    }

    @Override
    public void addScore(Score score) {

        scoreRepository.save(score);
    }

    @Override
    public Score createScore(String nationalId) {


        Score score = new Score();
        int randomScore = ThreadLocalRandom.current().nextInt(0,1500);
        score.setNationalNo(nationalId);
        score.setCreditScore(randomScore);
        return scoreRepository.save(score);

    }


}
