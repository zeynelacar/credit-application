package com.project.creditmangement.controller;

import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Result;
import com.project.creditmangement.model.Score;
import com.project.creditmangement.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ResultController {

    private final ResultService resultService;


    @GetMapping(value ="/results")
    public List<Result> getAllResults(){
        return  resultService.getAllResults();
    }

    @GetMapping(value ="/search")
    public Result searchResult(@RequestParam String nationalId){
        return  resultService.getResult(nationalId);
    }

    @PostMapping(value ="/apply")
    public String makeApplication(@RequestBody Applicant applicant){
        return resultService.makeApplication(applicant);
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteResult(@RequestParam @Min(1) String nationalId) {
        return resultService.deleteResult(nationalId);
    }


}
