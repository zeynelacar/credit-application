package com.project.creditmangement.controller;


import com.project.creditmangement.model.dto.ApplicantDTO;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.mapper.ApplicantMapper;
import com.project.creditmangement.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantService applicantService;


    @GetMapping(value ="/all")
    public List<ApplicantDTO> getAllApplicants(){
        List<Applicant> applicants = applicantService.getAllApplicants();
        return applicants.stream().map(ApplicantMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(value ="/find")
    public ResponseEntity<?> findApplicant(@RequestParam String nationalId){
        ResponseEntity<?> res = null;
        Applicant applicant = applicantService.getApplicant(nationalId);
        ApplicantDTO applicantDTO = ApplicantMapper.toDto(applicant);
        res = new ResponseEntity<>(applicantDTO, HttpStatus.OK);
        return res;
    }

    @PostMapping(value ="/add")
    public void addApplicant(@RequestBody ApplicantDTO applicantDTO){
        applicantService.addApplicant(ApplicantMapper.toEntity(applicantDTO));
    }

    @PutMapping(value="/update")
    public Applicant updateApplicant(@RequestBody ApplicantDTO applicantDTO){
        return applicantService.updateApplicant(ApplicantMapper.toEntity(applicantDTO));
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteApplicant(@RequestParam @Min(1) String nationalId) {
        return applicantService.deleteApplicant(nationalId);
    }

}
