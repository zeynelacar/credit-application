package com.project.creditmangement.service.implementations;

import com.project.creditmangement.cache.Cache;
import com.project.creditmangement.cache.CacheManager;
import com.project.creditmangement.exception.NotFoundException;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.repository.ApplicantRepository;
import com.project.creditmangement.service.ApplicantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    private final CacheManager cacheManager;


    @Autowired
    public void createCache(){
        List<Applicant> allApplicants = applicantRepository.findAll();
        if(!allApplicants.isEmpty())
            cacheManager.cacheAll("Applicants",allApplicants);
    }

    @Override
    public List<Applicant> getAllApplicants() {
        List<Applicant> applicants = (List<Applicant>) (Object) cacheManager.getAll("Applicants");
        return applicants;
    }
    @Override
    public Applicant getApplicant(String nationalId) {

        try {
            Applicant applicant = (Applicant) cacheManager.get("Applicants",nationalId);
            return applicant;
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
        applicantRepository.delete(getApplicant((nationalId)));
        return true;
    }
}
