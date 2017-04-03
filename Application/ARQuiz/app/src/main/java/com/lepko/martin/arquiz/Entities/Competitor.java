package com.lepko.martin.arquiz.Entities;

import java.util.List;

/**
 * Created by Martin on 25.2.2017.
 */

public class Competitor {

    private int id;
    private int competitionId;
    private List<Integer> answeredQuestions;

    public Competitor(int id, int competitionId, List<Integer> answeredQuestions) {
        this.id = id;
        this.competitionId = competitionId;
        this.answeredQuestions = answeredQuestions;
    }

    public int getId() {
        return id;
    }


    public int getCompetitionId() {
        return competitionId;
    }

    public List<Integer> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Integer> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public void addAnsweredQuestion(int questionId) {
        answeredQuestions.add(questionId);
    }
}
