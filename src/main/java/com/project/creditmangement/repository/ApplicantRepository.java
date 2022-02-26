package com.project.creditmangement.repository;

import com.project.creditmangement.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Integer> {

    Applicant findByNationalNo(String nationalId);

    public Boolean existsByNationalNo(String nationalId);

}
