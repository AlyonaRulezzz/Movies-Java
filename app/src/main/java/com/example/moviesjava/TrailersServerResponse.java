package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

public class TrailersServerResponse {
    @SerializedName("videos")
    private Videos videos;

    @Override
    public String toString() {
        return "TrailersServerResponse{" +
                "videos=" + videos +
                '}';
    }

    public TrailersServerResponse(Videos videos) {
        this.videos = videos;
    }

    public Videos getVideos() {
        return videos;
    }
}
