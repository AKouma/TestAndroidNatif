package com.example.akoumare.testandroidnatif;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by akoumare on 02/03/2018.
 */

public class Films extends RealmObject {


    @SerializedName("Poster")
    private String poster ;

    @SerializedName("Title")
    private String title;

    @SerializedName("Type")
    private String genre;

    @SerializedName("Year")
    private String year;

    @SerializedName("Actors")
    private String actors;



    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
