package com.project.creditmangement.service.implementations;

import com.project.creditmangement.cache.CacheManager;
import com.project.creditmangement.exception.NotFoundException;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.entity.Result;
import com.project.creditmangement.repository.ResultRepository;
import com.project.creditmangement.service.ApplicantService;
import com.project.creditmangement.service.ResultService;
import com.project.creditmangement.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Autowired
    private final ScoreService scoreService;

    private final ApplicantService applicantService;

    private final CacheManager cacheManager;
    @Autowired
    public void createCache(){
        List<Result> allResults = resultRepository.findAll();
        if(!allResults.isEmpty())
            cacheManager.cacheAll("Results",allResults);
    }
    @Override
    public List<Result> getAllResults() {
        try {
            List<Result> results = (List<Result>) (Object) cacheManager.getAll("Results");
            return results;
        }catch(Exception e){
            throw new NotFoundException("No result found in the system");
        }
    }

    @Override
    public Result getResult(String nationalId) {
        try{
            return resultRepository.findByNationalNo(nationalId);
        }catch(Exception e){
            throw new NotFoundException("Result");
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
    public String createResult(String nationalId, Integer monthlyIncome) {


        Result result = new Result();
        Integer score = scoreService.getScore(nationalId).getCreditScore();
        if(score<500){
            result.setApplicationResult("Denied");
            result.setLimit(0);
        }else{
            result.setApplicationResult("Approved");
            result.setLimit(calculateLimit(score,monthlyIncome));
        }
        result.setNationalNo(nationalId);
        resultRepository.save(result);
        String finalRes = "Result of your application:" + result.getApplicationResult() + "  " + " Your credit limit is:" + result.getLimit().toString();
        return (finalRes);

    }

    @Override
    public String makeApplication(Applicant applicant) {
        if (!applicantService.questionApplicant(applicant.getNationalNo())) {
            applicantService.addApplicant(applicant);
            scoreService.createScore(applicant.getNationalNo());
        }
        return createResult(applicant.getNationalNo(),applicant.getMonthlyIncome());
    }


    @Override
    public boolean deleteResult(String nationalId) {
        resultRepository.delete(getResult(nationalId));
        return true;
    }
}
