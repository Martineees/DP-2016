package com.lepko.martin.arquiz.Entities;

/**
 * Created by Martin on 25.2.2017.
 */

public class User {

    private String name;
    private int id;
    private boolean isAdmin;

    public User(String name, int id, boolean isAdmin) {
        this.name = name;
        this.id = id;
        this.isAdmin = isAdmin;
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
}
