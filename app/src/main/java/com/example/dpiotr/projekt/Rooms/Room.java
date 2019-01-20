package com.example.dpiotr.projekt.Rooms;

/**
 * Created by dpiotr on 12.12.16.
 */

public class Room {

    String name;
    String number;
    int id;

    public Room(String name, String number, int id) {
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
