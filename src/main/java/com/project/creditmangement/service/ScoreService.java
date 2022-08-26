package com.project.creditmangement.service;

import com.project.creditmangement.model.entity.Score;

public interface ScoreService {

    void addScore(Score score);

    Score getScore(String nationalId);

    Boolean createScore(String nationalId);

}
