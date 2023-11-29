package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {

    @SerializedName("docs")
    private List<Movie> movies;

    public ServerResponse(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
