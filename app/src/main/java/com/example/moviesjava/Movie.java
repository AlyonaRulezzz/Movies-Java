package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("rating")
    private Rating rating;

    @SerializedName("poster")
    private Poster poster;

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("year")
    private int year;

    public Movie(Rating rating, Poster poster, int id, String name, String description, int year) {
        this.rating = rating;
        this.poster = poster;
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
    }

    public Rating getRating() {
        return rating;
    }

    public Poster getPoster() {
        return poster;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rating=" + rating +
                ", poster=" + poster +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                '}';
    }

}
