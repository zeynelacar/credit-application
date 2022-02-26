package com.project.creditmangement.service.implementations;

import com.project.creditmangement.exception.NotFoundException;
import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.repository.ApplicantRepository;
import com.project.creditmangement.service.ApplicantService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public Applicant getApplicant(String nationalId) {

        try {
            Applicant applicant = applicantRepository.findByNationalNo(nationalId);
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
