package com.project.creditmanagement.service.implementations;

import com.project.creditmanagement.cache.CacheManager;
import com.project.creditmanagement.exception.NotFoundException;
import com.project.creditmanagement.model.entity.Applicant;
import com.project.creditmanagement.model.entity.Result;
import com.project.creditmanagement.repository.ResultRepository;
import com.project.creditmanagement.service.ApplicantService;
import com.project.creditmanagement.service.ResultService;
import com.project.creditmanagement.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    @Autowired
    private final ScoreService scoreService;

    private final ApplicantService applicantService;

    private final CacheManager cacheManager = new CacheManager();

    private final Logger logger = LogManager.getLogger();
    @Autowired
    public void createCache(){
        List<Result> allResults = resultRepository.findAll();
        if(!allResults.isEmpty())
            cacheManager.cacheAll("Results",allResults);
    }
    @Override
    public List<Result> getAllResults() {
        try {
            while(isCacheAvail()){
                List<Result> results = (List<Result>) (Object) cacheManager.getAll("Results");
                return results;
            } return resultRepository.findAll();
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

    private boolean isCacheAvail(){
        if(!cacheManager.getTrueCache().isEmpty()){
            return true;
        }else{
            logger.error("Cache system currently not working");
            return false;
        }
    }
}
