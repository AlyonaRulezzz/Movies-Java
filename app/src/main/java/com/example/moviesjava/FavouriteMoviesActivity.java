package com.example.moviesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    FavouriteMoviesViewModel favouriteMoviesViewModel;
    MoviesAdapter moviesAdapter;
    RecyclerView recyclerViewFavouriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        moviesAdapter = new MoviesAdapter();
        recyclerViewFavouriteMovies = findViewById(R.id.rvFavourites);
        recyclerViewFavouriteMovies.setAdapter(moviesAdapter);
        recyclerViewFavouriteMovies.setLayoutManager(new GridLayoutManager(this, 2));

        favouriteMoviesViewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        favouriteMoviesViewModel.getAllFavouriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });

        moviesAdapter.setOnFilmListener(new MoviesAdapter.IOnFilmListener() {
            @Override
            public void openDetailActivity(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}