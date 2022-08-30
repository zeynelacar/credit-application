package com.project.creditmanagement.service;

import com.project.creditmanagement.model.entity.Score;

public interface ScoreService {

    void addScore(Score score);

    Score getScore(String nationalId);

    Boolean createScore(String nationalId);

}
