package com.project.creditmanagement.repository;

import com.project.creditmanagement.model.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant,Integer> {

    Applicant findByNationalNo(String nationalId);

    public void deleteByNationalNo(String nationalId);

    public Boolean existsByNationalNo(String nationalId);

}
