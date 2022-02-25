package com.project.creditmangement.service;

import com.project.creditmangement.model.Applicant;
import com.project.creditmangement.model.Score;


import java.util.List;

public interface ScoreService {

    void addScore(Score score);

    Score getScore(String nationalId);

    Score createScore(String nationalId);

}
