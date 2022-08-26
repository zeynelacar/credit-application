package com.project.creditmangement.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResultDTO {

    @NotBlank
    private String nationalNo;

    @NotBlank
    private String applicationResult;

    @NotBlank
    private Integer limit;

}
