package com.example.moviesjava;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel1";

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void loadMovies() {
        Disposable disposable = ApiFactory.apiService.loadMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ServerResponse>() {
                    @Override
                    public void accept(ServerResponse serverResponse) throws Throwable {
                        List<Movie> moviesToAdd = serverResponse.getMovies();
                        Log.d("Adapter", movies.getValue().toString());
                        if (movies.getValue() != null) {
                            movies.getValue().addAll(moviesToAdd);
                            movies.setValue(movies.getValue());
                        } else {
                            movies.setValue(serverResponse.getMovies());
                        }
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
