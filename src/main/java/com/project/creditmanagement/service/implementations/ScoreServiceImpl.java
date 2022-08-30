package com.project.creditmanagement.service.implementations;

import com.project.creditmanagement.exception.InvalidRequestException;
import com.project.creditmanagement.model.entity.Score;
import com.project.creditmanagement.repository.ScoreRepository;
import com.project.creditmanagement.service.ApplicantService;
import com.project.creditmanagement.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    private final ApplicantService applicantService;



    @Override
    public Score getScore(String nationalId) {
        try{
            if (!applicantService.questionApplicant(nationalId)) {
                createScore(nationalId);
            }
            return scoreRepository.findByNationalNo(nationalId);
        }catch(Exception e){
            throw new InvalidRequestException("Invalid Request");
        }
    }

    @Override
    public void addScore(Score score) {

        scoreRepository.save(score);
    }

    @Override
    public Boolean createScore(String nationalId) {


        Score score = new Score();
        int randomScore = ThreadLocalRandom.current().nextInt(0,1500);
        score.setNationalNo(nationalId);
        score.setCreditScore(randomScore);
        scoreRepository.save(score);
        return true;

    }


}
