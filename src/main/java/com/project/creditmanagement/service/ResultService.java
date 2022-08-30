package com.project.creditmanagement.service;

import com.project.creditmanagement.model.entity.Applicant;
import com.project.creditmanagement.model.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ResultService {

    List<Result> getAllResults();

    Result getResult(String nationalId);

    String createResult(String nationalId, Integer monthlyIncome);

    String makeApplication(@RequestBody Applicant applicant);

    boolean deleteResult(String nationalId);

}
