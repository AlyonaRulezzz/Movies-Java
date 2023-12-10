package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private Double kp;

    public Rating(Double kp) {
        this.kp = kp;
    }

    public Double getKp() {
        return kp;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "kp=" + kp +
                '}';
    }

}
