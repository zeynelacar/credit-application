package com.project.creditmangement.service;

import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ResultService {

    List<Result> getAllResults();

    Result getResult(String nationalId);

    String createResult(@RequestBody Applicant applicant);

    String makeApplication(@RequestBody Applicant applicant);

    Result updateResult(@RequestBody Applicant applicant);

    boolean deleteResult(String nationalId);

}
