package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsServerResponse {
    @SerializedName("docs")
    private List<Review> reviews;

    public ReviewsServerResponse(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "ReviewsServerResponse{" +
                "reviews=" + reviews +
                '}';
    }
}
