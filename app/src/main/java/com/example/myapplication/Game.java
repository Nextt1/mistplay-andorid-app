/**
 * @desc this class is the model to store and access the game data.
 * @author Dhruvin Pipalia dhruvinhi@gmail.com
 */
package com.example.myapplication;

import java.util.ArrayList;

public class Game {
    private String genre;
    private String imgURL;
    private String subgenre;
    private String title;
    private String pid;
    private float rating;
    private long rCount;

    public Game(String genre, String imgURL, String subgenre, String title, String pid, float rating, long rCount){
        this.genre = genre;
        this.imgURL = imgURL;
        this.subgenre = subgenre;
        this.title = title;
        this.pid = pid;
        this.rating = rating;
        this.rCount = rCount;
    }

    public String getTitle(){
        return this.title;
    }

    public String getLogo(){
        return this.imgURL;
    }

    public String getRating(){ return String.valueOf(this.rating); }

    public String getSubgenre(){
        return this.subgenre;
    }

    public long getRCount(){
        return this.rCount;
    }

    private static int lastContactId = 0;

}
