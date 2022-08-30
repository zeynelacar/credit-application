package com.project.creditmanagement.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApplicantDTO {

    private Integer id;

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
