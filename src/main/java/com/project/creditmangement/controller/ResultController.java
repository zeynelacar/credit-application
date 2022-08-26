package com.project.creditmangement.controller;

import com.project.creditmangement.model.dto.ApplicantDTO;
import com.project.creditmangement.model.dto.ResultDTO;
import com.project.creditmangement.model.entity.Applicant;
import com.project.creditmangement.model.entity.Result;
import com.project.creditmangement.model.mapper.ApplicantMapper;
import com.project.creditmangement.model.mapper.ResultMapper;
import com.project.creditmangement.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ResultController {

    private final ResultService resultService;


    @GetMapping(value ="/results")
    public List<ResultDTO> getAllResults(){
        List<Result> results =   resultService.getAllResults();
        return results.stream().map(ResultMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(value ="/search")
    public ResponseEntity<?> searchResult(@RequestParam String nationalId){
        ResponseEntity<?> res = null;
        Result result = resultService.getResult(nationalId);
        ResultDTO resultDTO = ResultMapper.toDto(result);
        res = new ResponseEntity<>(resultDTO, HttpStatus.OK);
        return res;
    }

    @PostMapping(value ="/apply")
    public String makeApplication(@RequestBody ApplicantDTO applicantDTO){
        return resultService.makeApplication(ApplicantMapper.toEntity(applicantDTO));
    }

    @DeleteMapping(value = "/delete")
    public boolean deleteResult(@RequestParam @Min(1) String nationalId) {
        return resultService.deleteResult(nationalId);
    }


}
