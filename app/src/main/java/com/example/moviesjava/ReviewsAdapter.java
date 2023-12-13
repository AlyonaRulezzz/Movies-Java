package com.example.moviesjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvReviewAuthor;
        private TextView tvReview;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewAuthor = itemView.findViewById(R.id.tvReviewAuthor);
            tvReview = itemView.findViewById(R.id.tvReview);
        }
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_review_item_layout, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvReviewAuthor.setText(review.getAuthor());
        holder.tvReview.setText(review.getReview());
        int background;
        if (Objects.equals(review.getType(), "Позитивный")) {
            background = R.color.green;
        } else if (Objects.equals(review.getType(), "Негативный")) {
            background = R.color.red;
        } else { //  (Objects.equals(review.getType(), "Нейтральный"))
            background = R.color.yellow;
        }
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), background));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
