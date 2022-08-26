package com.project.creditmangement.repository;

import com.project.creditmangement.model.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Integer> {


    Score findByNationalNo(String nationalId);


}
