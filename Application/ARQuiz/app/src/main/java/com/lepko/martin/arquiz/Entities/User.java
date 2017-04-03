package com.lepko.martin.arquiz.Entities;

import java.util.List;

/**
 * Created by Martin on 25.2.2017.
 */

public class User {

    private String name;
    private int id;
    private boolean isAdmin;
    private List<Competitor> competitors;

    public User(String name, int id, boolean isAdmin, List<Competitor> competitors) {
        this.name = name;
        this.id = id;
        this.isAdmin = isAdmin;
        this.competitors = competitors;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Competitor> getCompetitors() {
        return competitors;
    }

    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public boolean isInCompetition(int competitionId) {

        for(Competitor competitor : competitors) {
            if(competitor.getCompetitionId() == competitionId)
                return true;
        }

        return false;
    }

    public int getCompetitorId(int competitionId) {

        for(Competitor competitor : competitors) {
            if(competitor.getCompetitionId() == competitionId)
                return competitor.getId();
        }

        return -1;
    }

    public Competitor getCompetitorByCompetitionId(int competitionId) {

        for(Competitor competitor : competitors) {
            if(competitor.getCompetitionId() == competitionId)
                return competitor;
        }

        return null;
    }

    public void updateCompetitorAnsweredQuestionList(Competitor competitor) {
        for(Competitor c : competitors) {
            if (c.getId() == competitor.getId())
                c.setAnsweredQuestions(competitor.getAnsweredQuestions());
        }
    }
}
