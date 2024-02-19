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
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {
    public static final String TAG = "MovieDetailViewModel1";

    public final MovieDao movieDao;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);

        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();
    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return movieDao.getFavouriteMovie(movieId);
    }

    public void insertMovie(Movie movie) {
        Disposable disposable = movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void deleteMovie(int movieId) {
        Disposable disposable = movieDao.deleteMovie(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void loadTrailers(Movie movie) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(movie.getId())
                .subscribeOn(Schedulers.io())
                .map(new Function<TrailersServerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailersServerResponse trailersServerResponse) throws Throwable {
                        return trailersServerResponse.getVideos().getTrailers();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {
                        trailers.setValue(trailerList);
                        Log.d(TAG, trailerList.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public  void loadReviews(Movie movie) {
        Disposable disposable = ApiFactory.apiService.loadReviews(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewsServerResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewsServerResponse reviewsServerResponse) throws Throwable {
                        return reviewsServerResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviewsListResponse) throws Throwable {
                        reviews.setValue(reviewsListResponse);
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
