package com.project.creditmangement.service;

import com.project.creditmangement.model.Applicant;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ApplicantService {

    List<Applicant> getAllApplicants();

    Applicant getApplicant(String nationalId);

    Boolean questionApplicant(String nationalId);

    void addApplicant(@RequestBody Applicant applicant);

    Applicant updateApplicant(@RequestBody Applicant applicant);

    boolean deleteApplicant(String nationalId);

}
