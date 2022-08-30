package com.project.creditmanagement.service.implementations;

import com.project.creditmanagement.cache.CacheManager;
import com.project.creditmanagement.exception.NotFoundException;
import com.project.creditmanagement.model.entity.Applicant;
import com.project.creditmanagement.repository.ApplicantRepository;
import com.project.creditmanagement.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service

public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    private final CacheManager cacheManager = new CacheManager();

    private final Logger logger = LogManager.getLogger();
    @Autowired
    public void createCache(){
        List<Applicant> allApplicants = applicantRepository.findAll();
        if(!allApplicants.isEmpty())
            cacheManager.cacheAll("Applicants",allApplicants);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        try {
            while (isCacheAvail()) {
                List<Applicant> applicants = (List<Applicant>) (Object) cacheManager.getAll("Applicants");
                return applicants;
            }return applicantRepository.findAll();
        }catch(Exception ex){
            throw new NotFoundException("No applicant found");
        }

    }
    @Override
    public Applicant getApplicant(String nationalId) {

        try {
            while (isCacheAvail()){
            Applicant applicant = (Applicant) cacheManager.get("Applicants",nationalId);
            return applicant;
            } return applicantRepository.findByNationalNo(nationalId);
        }catch(Exception e){
            throw new NotFoundException("Applicant");
        }
            //throw new NotFoundException("Applicant");

    }

    @Override
    public Boolean questionApplicant(String nationalId) {
        return applicantRepository.existsByNationalNo(nationalId);
    }


    @Override
    public void addApplicant(Applicant applicant) {

        applicantRepository.save(applicant);

    }

    @Override
    public Applicant updateApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public boolean deleteApplicant(String nationalId) {
        applicantRepository.deleteByNationalNo(nationalId);
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
