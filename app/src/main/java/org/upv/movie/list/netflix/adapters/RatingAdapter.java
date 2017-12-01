package org.upv.movie.list.netflix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.User;

import java.util.List;

/**
 * Created by Miguel Á. Núñez on 25/11/2017.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    private List<User> items;
    private int idPelicula;

    public RatingAdapter(List<User> items, int idPelicula) {
        this.items = items;
        this.idPelicula = idPelicula;
    }

    @Override
    public RatingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_row, parent, false);
        return new RatingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RatingViewHolder holder, int position) {
        String[] ratingComment = items.get(position).getRating(idPelicula).split("╩");
        holder.userRating.setRating(Float.parseFloat(ratingComment[0]));
        holder.userComment.setText(ratingComment[1]);
        holder.userName.setText(items.get(position).getUsername());
        holder.userImage.setImageResource(items.get(position).getDEFAULT_PHOTO());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage;
        private TextView userComment;
        private TextView userName;
        private RatingBar userRating;

        private RatingViewHolder(View v) {
            super(v);
            userImage = v.findViewById(R.id.user_image);
            userComment = v.findViewById(R.id.user_comment);
            userName = v.findViewById(R.id.user_name);
            userRating = v.findViewById(R.id.user_rating);
        }
    }
}
