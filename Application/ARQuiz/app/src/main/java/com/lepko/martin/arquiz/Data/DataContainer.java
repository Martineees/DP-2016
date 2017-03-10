package com.lepko.martin.arquiz.Data;

import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Question;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 9.3.2017.
 */

public class DataContainer {

    private List<Competition> competitions;
    private List<Question> questions;

    private static DataContainer instance = null;

    private DataContainer() {
        competitions = new LinkedList<>();
        questions = new LinkedList<>();
    }

    public static DataContainer getInstance() {
        if(instance == null)
            instance = new DataContainer();

        return instance;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }

    public Competition getCompetitionById(int id) {
        for (Competition c: competitions) {
            if(c.getId() == id) return c;
        }

        return null;
    }

    public Competition getCompetitionByIndex(int index) {
        if(index > -1 && index < competitions.size())
            return competitions.get(index);

        return null;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestionById(int id) {
        for (Question q: questions) {
            if(q.getId() == id) return q;
        }

        return null;
    }

    public Question getQuestionByIndex(int index) {
        if(index > -1 && index < questions.size())
            return questions.get(index);

        return null;
    }

    public List<Question> getQuestionsByLocation(int locationId) {
        List<Question> result = new LinkedList<>();

        for(Question q: questions) {
           if(q.getLocation().getId() == locationId)
               result.add(q);
        }

        return result;
    }
}
