package com.example.week9;

import java.time.LocalDateTime;

public class Teatteri {
    String name = null;
    String ID = null;

    public Teatteri (){

    }

    public Teatteri(String name, String Id){
        this.name = name;
        this.ID = Id;


    }

    public String getName(){
    return name;
    }

    public String getID(){
        return ID;
    }

}
