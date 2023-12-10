package com.example.moviesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity1";

    MainViewModel mainViewModel;
    MoviesAdapter moviesAdapter;
    RecyclerView recyclerViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesAdapter = new MoviesAdapter();
        recyclerViewMovies = findViewById(R.id.rv);
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
//                Log.d( TAG, String.valueOf(mainViewModel.getMovies().getValue().size()));
                Log.d(TAG, movies.get(0).getName());
                moviesAdapter.setMovies(movies);
//                Log.d( TAG, String.valueOf(mainViewModel.getMovies().getValue().size()));
            }
        });

        mainViewModel.loadMovies();

        moviesAdapter.setOnListEnd(new MoviesAdapter.IOnListEndListener() {
            @Override
            public void onReachedListEnd() {
                mainViewModel.loadMovies();
            }
        });
    }

}