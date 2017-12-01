package org.upv.movie.list.netflix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.movie.MovieList;
import org.upv.movie.list.netflix.utils.DownloadImageTask;

import java.util.List;

/**
 * Created by Miguel Á. Núñez on 11/11/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private List<Movie> items;

    public MovieListAdapter() {
        this.items = MovieList.list;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_row, parent, false);
        return new MovieListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.category.setText(items.get(position).getCategory());
        holder.rating.setRating(items.get(position).getAverageRating());
        holder.numRatings.setText(new StringBuilder().append("(").append(items.get(position).getNumRatings()).append(" user ratings)").toString());
        //Descargamos la imagen y se la añadimos al ImageView
        new DownloadImageTask(holder.poster).execute(items.get(position).getCardImageUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MovieListViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView title;
        private TextView category;
        private RatingBar rating;
        private TextView numRatings;

        private MovieListViewHolder(View v) {
            super(v);
            poster = v.findViewById(R.id.movie_poster);
            title = v.findViewById(R.id.movie_title);
            category = v.findViewById(R.id.movie_year);
            rating = v.findViewById(R.id.movie_rating);
            numRatings = v.findViewById(R.id.num_ratings);
        }
    }
}
