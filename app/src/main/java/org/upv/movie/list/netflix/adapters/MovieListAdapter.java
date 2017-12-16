package org.upv.movie.list.netflix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.utils.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel Á. Núñez on 11/11/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> implements Filterable {

    private List<Movie> items;
    private List<Movie> moviesFilter;
    private CustomFilter mFilter;

    public MovieListAdapter(List<Movie> movieList) {
        items = movieList;
        moviesFilter = new ArrayList<>();
        moviesFilter.addAll(items);
        mFilter = new CustomFilter(MovieListAdapter.this);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_row, parent, false);
        return new MovieListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.title.setText(moviesFilter.get(position).getTitle());
        holder.category.setText(moviesFilter.get(position).getCategory());
        holder.rating.setRating(moviesFilter.get(position).getAverageRating());
        String s = holder.itemView.getResources().getString(R.string.MLA_user_ratings);
        holder.numRatings.setText(new StringBuilder().append("(").append(moviesFilter.get(position).getNumRatings()).append(" " + s + ")").toString());
        //Descargamos la imagen y se la añadimos al ImageView
        new DownloadImageTask(holder.poster).execute(moviesFilter.get(position).getCardImageUrl());
    }

    @Override
    public int getItemCount() {
        return moviesFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
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

    /*Filtro*/
    public class CustomFilter extends Filter {
        private MovieListAdapter listAdapter;

        private CustomFilter(MovieListAdapter listAdapter) {
            super();
            this.listAdapter = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            moviesFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                moviesFilter.addAll(items);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Movie movie : items) {
                    if (movie.getTitle().contains(filterPattern) || movie.getCategory().contains(filterPattern)) {
                        moviesFilter.add(movie);
                    }
                }
            }
            results.values = moviesFilter;
            results.count = moviesFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

}
