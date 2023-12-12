package com.example.moviesjava;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

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
