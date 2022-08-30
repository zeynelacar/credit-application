package com.project.creditmanagement.model.mapper;

import com.project.creditmanagement.model.dto.ApplicantDTO;
import com.project.creditmanagement.model.entity.Applicant;

public class ApplicantMapper {

    public static ApplicantDTO toDto(Applicant applicant){
        ApplicantDTO applicantDTO = new ApplicantDTO();
        applicantDTO.setId(applicant.getId());
        applicantDTO.setName(applicant.getName());
        applicantDTO.setSurname(applicant.getSurname());
        applicantDTO.setNationalNo(applicant.getNationalNo());
        applicantDTO.setMonthlyIncome(applicant.getMonthlyIncome());
        applicantDTO.setPhone(applicant.getPhone());
        return applicantDTO;
    }

    public static Applicant toEntity(ApplicantDTO applicantDTO){
        Applicant applicant = new Applicant();
        applicant.setName(applicantDTO.getName());
        applicant.setSurname(applicantDTO.getSurname());
        applicant.setNationalNo(applicantDTO.getNationalNo());
        applicant.setMonthlyIncome(applicantDTO.getMonthlyIncome());
        applicant.setPhone(applicantDTO.getPhone());
        return applicant;
    }

}
