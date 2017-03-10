package com.lepko.martin.arquiz.Entities;

import java.util.List;

/**
 * Created by Martin on 6.3.2017.
 */

public class Question {

    private int id;
    private String name;
    private String targetId;
    private Location location;
    private int type;
    private int score;
    private List<Answer> answers;

    public Question(int id, String name, String targetId, Location location, int type, int score, List<Answer> answers) {
        this.id = id;
        this.name = name;
        this.targetId = targetId;
        this.location = location;
        this.type = type;
        this.score = score;
        this.answers = answers;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getTargetId() {
        return targetId;
    }

    public Location getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getScore() {
        return score;
    }
}
