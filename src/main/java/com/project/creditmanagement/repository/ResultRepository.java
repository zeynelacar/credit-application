package com.project.creditmanagement.repository;

import com.project.creditmanagement.model.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result,Integer> {

    Result findByNationalNo(String nationalId);

}
