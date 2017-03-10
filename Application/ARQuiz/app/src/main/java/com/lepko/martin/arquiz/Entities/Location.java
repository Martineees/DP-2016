package com.lepko.martin.arquiz.Entities;

/**
 * Created by Martin on 25.2.2017.
 */

public class Location {

    private int id;
    private char block;
    private int floor;

    public Location(int id, char block, int floor) {
        this.id = id;
        this.block = block;
        this.floor = floor;
    }

    public int getId() {
        return id;
    }

    public char getBlock() {
        return block;
    }

    public int getFloor() {
        return floor;
    }

    public String getLocation() {return Character.toString(block) + Integer.toString(floor); }
}
