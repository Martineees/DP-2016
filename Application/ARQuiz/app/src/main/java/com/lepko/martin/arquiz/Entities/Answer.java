package com.lepko.martin.arquiz.Entities;

/**
 * Created by Martin on 6.3.2017.
 */

public class Answer {

    private int id;
    private String name;
    private boolean isCorrect;

    public Answer(int id, String name, boolean isCorrect) {
        this.id = id;
        this.name = name;
        this.isCorrect = isCorrect;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
