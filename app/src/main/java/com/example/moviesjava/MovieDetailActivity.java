package com.example.moviesjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "movie";
    private static final String TAG = "MovieDetailActivity1";

    private MovieDetailViewModel movieDetailViewModel;

    private RecyclerView trailersRecyclerView;
    private RecyclerView reviewsRecyclerView;

    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        assert movie != null;
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(ivPoster);
        tvTitle.setText(movie.getName());
        tvYear.setText(String.valueOf(movie.getYear()));
        tvDescription.setText(movie.getDescription());

        TrailersAdapter trailersAdapter = new TrailersAdapter();
        trailersRecyclerView.setAdapter(trailersAdapter);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        movieDetailViewModel.loadTrailers(movie);

        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailerList) {
                Log.d(TAG, trailerList.toString());
                trailersAdapter.setTrailers(trailerList);
            }
        });

        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.IOnTrailerClickListener() {
            @Override
            public void onTrailerClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter();
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieDetailViewModel.loadReviews(movie);

        MovieDao movieDao = MovieDatabase.getInstance(getApplication())
                        .movieDao();
        movieDao.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .subscribe();

        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.setReviews(reviews);
            }
        });
    }

    private void initViews() {
        ivPoster = findViewById(R.id.ivPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvYear);
        tvDescription = findViewById(R.id.tvDescription);
        trailersRecyclerView = findViewById(R.id.trailersRecyclerView);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}