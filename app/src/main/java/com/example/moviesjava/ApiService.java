package com.example.moviesjava;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

interface ApiService {

    @GET("movie?token=K5416T0-EJ04XMY-QCB0F37-BXZMYWW")
    Single<ServerResponse> loadMovies();
}
