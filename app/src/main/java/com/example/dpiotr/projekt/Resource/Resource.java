package com.example.dpiotr.projekt.Resource;

/**
 * Created by dpiotr on 10.11.16.
 */

public class Resource {

    String name;
    String room;
    String owner;
    String state;
    public Resource(String name, String room,String owner,String state) {
        this.name = name;
        this.room = room;
        this.owner = owner;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
