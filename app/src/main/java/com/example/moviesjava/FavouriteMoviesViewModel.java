package com.example.moviesjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavouriteMoviesViewModel extends AndroidViewModel {
    public final MovieDao movieDao;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);

        movieDao = MovieDatabase.getInstance(getApplication()).movieDao();
    }

   public LiveData<List<Movie>> getAllFavouriteMovies() {
        return movieDao.getAllFavouriteMovies();
   }
}
