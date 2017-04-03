package com.lepko.martin.arquiz.Entities;

/**
 * Created by Martin on 24.3.2017.
 */

public class ChartEntity {

    private int competitorId;
    private int position;
    private String userName;
    private int points;

    public ChartEntity(int competitorId, int position, String userName, int points) {
        this.competitorId = competitorId;
        this.position = position;
        this.userName = userName;
        this.points = points;
    }

    public int getCompetitorId() {
        return competitorId;
    }

    public int getPosition() {
        return position;
    }

    public String getUserName() {
        return userName;
    }

    public int getPoints() {
        return points;
    }
}
