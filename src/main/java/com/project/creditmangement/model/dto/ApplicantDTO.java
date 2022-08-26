package com.project.creditmangement.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApplicantDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String nationalNo;

    @NotBlank
    private Integer monthlyIncome;

    @NotBlank
    private String phone;

}
