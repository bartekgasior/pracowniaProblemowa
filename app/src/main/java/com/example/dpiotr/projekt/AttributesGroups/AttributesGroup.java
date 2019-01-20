package com.example.dpiotr.projekt.AttributesGroups;

/**
 * Created by Bartek on 02.01.2017.
 */

public class AttributesGroup {
    private String name;

    public AttributesGroup(){}

    public AttributesGroup(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String nazwa){
        this.name=nazwa;
    }

}
