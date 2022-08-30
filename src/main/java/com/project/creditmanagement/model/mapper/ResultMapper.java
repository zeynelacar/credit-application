package com.project.creditmanagement.model.mapper;

import com.project.creditmanagement.model.dto.ResultDTO;
import com.project.creditmanagement.model.entity.Result;

public class ResultMapper {

    public static ResultDTO toDto(Result result){
        ResultDTO resultDto = new ResultDTO();
        resultDto.setNationalNo(result.getNationalNo());
        resultDto.setApplicationResult(result.getApplicationResult());
        resultDto.setLimit(result.getLimit());
        return resultDto;
    }

    public static Result toEntity(ResultDTO resultDTO){
        Result result = new Result();
        result.setLimit(resultDTO.getLimit());
        result.setNationalNo(resultDTO.getNationalNo());
        result.setApplicationResult(result.getApplicationResult());
        return result;
    }

}
