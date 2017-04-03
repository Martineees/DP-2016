package com.lepko.martin.arquiz.Data;

import android.util.Log;

import com.lepko.martin.arquiz.Entities.Competition;
import com.lepko.martin.arquiz.Entities.Competitor;
import com.lepko.martin.arquiz.Entities.Question;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Martin on 9.3.2017.
 */

public class DataContainer {

    private List<Competition> competitions;
    private List<Question> questions;
    private Competition currentCompetition;

    private static DataContainer instance = null;

    private DataContainer() {
        competitions = new LinkedList<>();
        questions = new LinkedList<>();
        currentCompetition = null;
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
        Log.i("DataContainer", questions.toString());
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

    public List<Question> getQuestionsByLocation(int locationId, List<Integer> except) {
        List<Question> result = new LinkedList<>();

        for(Question q: questions) {
            if(q.getLocation().getId() == locationId)
                if(!except.contains(q.getId()))
                    result.add(q);
        }

        return result;
    }

    public Competition getCurrentCompetition() {
        return currentCompetition;
    }

    public void setCurrentCompetition(Competition currentCompetition) {
        this.currentCompetition = currentCompetition;
    }
}
