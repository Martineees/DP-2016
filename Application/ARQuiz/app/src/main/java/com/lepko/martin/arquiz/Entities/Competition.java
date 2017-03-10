package com.lepko.martin.arquiz.Entities;

/**
 * Created by Martin on 6.3.2017.
 */

public class Competition {

    private int id;
    private String name;
    private int ownerId;
    private String created;
    private String description;

    public Competition(int id, String name, int ownerId, String created, String description) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.created = created;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
