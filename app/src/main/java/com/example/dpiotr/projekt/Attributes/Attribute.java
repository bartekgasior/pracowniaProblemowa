package com.example.dpiotr.projekt.Attributes;

/**
 * Created by Bartek on 02.01.2017.
 */

public class Attribute {
    String type;
    String name;
    int ID;

    public Attribute(){}

    public Attribute( String name, String type, int ID){
        this.type=type;
        this.name=name;
        this.ID=ID;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String nazwa){
        this.name = nazwa;
    }

    public int getID() {return ID;}
    public void setID(int idx){ this.ID=idx;}
}
