package com.project.creditmangement.repository;

import com.project.creditmangement.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Integer> {

    Applicant findByNationalNo(String nationalId);

    Boolean existsByNationalNo(String nationalId);

}
