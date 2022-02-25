package com.project.creditmangement.controller;


import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/applicant")
public class ApplicantController {

    private final ApplicantService applicantService;


    @GetMapping(value ="/all")
    public List<Applicant> getAllApplicants(){
        return  applicantService.getAllApplicants();
    }

    @GetMapping(value ="/find")
    public Applicant findApplicant(@RequestParam String nationalId){
        return  applicantService.getApplicant(nationalId);
    }

    @PostMapping(value ="/add")
    public void addFlat(@RequestBody Applicant applicant){
        applicantService.addApplicant(applicant);
    }

    @PutMapping(value="/update")
    public Applicant updateFlat(@RequestBody Applicant applicant){
        return applicantService.updateApplicant(applicant);
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteFlat(@RequestParam @Min(1) String nationalId) {
        return applicantService.deleteApplicant(nationalId);
    }

}
