package com.example.moviesjava;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();

    private IOnListEndListener onListEnd;

    private IOnFilmListener onFilmListener;
    public void setOnListEnd(IOnListEndListener onListEnd) {
        this.onListEnd = onListEnd;
    }
    public void setOnFilmListener(IOnFilmListener onFilmListener) {
        this.onFilmListener = onFilmListener;
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView tvRating;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Log.d("Adaper1", "position" + position);
        Movie movie = movies.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.ivPoster);
        Double rating = movie.getRating().getKp();
        holder.tvRating.setText(String.valueOf(rating).substring(0, 3));
        int backgroundId = R.drawable.rating_background_red;
        if (rating >= 8 && rating < 9) {
            backgroundId = R.drawable.rating_background_orange;
        } else if (rating >= 9) {
            backgroundId = R.drawable.rating_background_green;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.tvRating.setBackground(background);

        if ( (position >= movies.size() - 10) && (onListEnd != null) ) {
//        if ( (onListEnd != null) ) {
            onListEnd.onReachedListEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilmListener.openDetailActivity(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
//        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    interface IOnListEndListener {
        void onReachedListEnd();
    }

    interface IOnFilmListener {
        void openDetailActivity(Movie movie);
    }
}
