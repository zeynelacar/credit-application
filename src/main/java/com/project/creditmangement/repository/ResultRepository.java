package com.project.creditmangement.repository;

import com.project.creditmangement.model.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result,Integer> {

    Result findByNationalNo(String nationalId);

}
