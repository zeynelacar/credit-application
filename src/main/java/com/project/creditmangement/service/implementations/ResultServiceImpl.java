package com.project.creditmangement.service.implementations;

import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Result;
import com.project.creditmangement.repository.ResultRepository;
import com.project.creditmangement.service.ApplicantService;
import com.project.creditmangement.service.ResultService;
import com.project.creditmangement.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final ScoreService scoreService;

    private final ApplicantService applicantService;


    @Override
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    @Override
    public Result getResult(String nationalId) {
        try{
            return resultRepository.findByNationalNo(nationalId);
        }catch(Exception e){
            throw new RuntimeException("Result not found");
        }
    }

    public Integer calculateLimit(Integer score,Integer monthlyIncome){


         int limit = 0;

         try{
             if(score>=500&&score<1000){
                 if(monthlyIncome<5000){

                     limit = 10000;
                 }else{
                     limit = 20000;
                 }
             }else if(score >= 1000){

                 limit = monthlyIncome*4 ;
             }else{
                 System.out.println("Limit can not be calculated");
             }return limit;
         }catch(Exception e){

            throw  new RuntimeException("Service unavailable");

        }



    }

    @Override
    public String createResult(Applicant applicant) {


        Result result = new Result();
        Integer monthlyIncome =applicant.getMonthlyIncome();
        Integer score = scoreService.getScore(applicant.getNationalNo()).getCreditScore();
        if(score<500){
            result.setApplicationResult("Denied");
        }else{
            result.setApplicationResult("Approved");
            result.setLimit(calculateLimit(score,monthlyIncome));
        }
        result.setNationalNo(applicant.getNationalNo());
        resultRepository.save(result);
        return result.getApplicationResult();

    }

    @Override
    public String makeApplication(Applicant applicant) {
        if (!applicantService.questionApplicant(applicant.getNationalNo())) {
            applicantService.addApplicant(applicant);
            scoreService.createScore(applicant.getNationalNo());
        }
        return createResult(applicant);
    }

    @Override
    public Result updateResult(Applicant applicant) {
        return null;
    }

    @Override
    public boolean deleteResult(String nationalId) {
        return false;
    }
}
