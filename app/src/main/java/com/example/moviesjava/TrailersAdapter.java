package com.example.moviesjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {
    private List<Trailer> trailers = new ArrayList<>();

    private IOnTrailerClickListener onTrailerClickListener;
    public void setOnTrailerClickListener(IOnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }
    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    static class TrailersViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTrailerTitle;
        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTrailerTitle = itemView.findViewById(R.id.tvTrailerTitle);
        }
    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_trailer_item_layout, parent, false);
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.tvTrailerTitle.setText(trailer.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTrailerClickListener != null)
                    onTrailerClickListener.onTrailerClick(trailer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    interface IOnTrailerClickListener {
        void onTrailerClick(Trailer trailer);
    }
}
