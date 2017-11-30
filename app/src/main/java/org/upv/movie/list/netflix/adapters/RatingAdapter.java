package org.upv.movie.list.netflix.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.User;

import java.util.Set;

/**
 * Created by Miguel Á. Núñez on 25/11/2017.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    private Set<String> items;
    private int idPelicula;

    public RatingAdapter(Set<String> items, int idPelicula) {
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
        int i = 0;
        Gson gson = new Gson();

        for(String anUserList : items) {
            if(i == position) {
                User userAux = gson.fromJson(anUserList, User.class);
                if(!userAux.getRating(idPelicula).equals("0.0f- ")) {
                    String[] ratingComment = userAux.getRating(idPelicula).split("-");
                    holder.userRating.setRating(Float.parseFloat(ratingComment[0]));
                    holder.userComment.setText(ratingComment[1]);
                    holder.userName.setText(userAux.getUsername());
                    holder.userImage.setImageResource(userAux.getDEFAULT_PHOTO());
                    break;
                }
            } else {
                i++;
            }
        }
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
