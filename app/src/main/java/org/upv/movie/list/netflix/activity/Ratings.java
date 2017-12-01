package org.upv.movie.list.netflix.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;

import com.google.gson.Gson;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.adapters.RatingAdapter;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.model.User;
import org.upv.movie.list.netflix.movie.MovieList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ratings extends AppCompatActivity {

    private Set<String> userList = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        List<Movie> movieList = MovieList.list;

        int idPelicula = getIntent().getExtras().getInt("idPelicula");

        SharedPreferences prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);
        userList = prefs.getStringSet("users", userList);

        //Nos quedamos solo con los que han votado a la pelicula con idPelicula
        List<User> userRatingMovie = new ArrayList<User>();
        Gson gson = new Gson();
        for (String anUserList : userList) {
            User userAux = gson.fromJson(anUserList, User.class);
            if (!userAux.getRating(idPelicula).equals("0.0f╩ ")) {
                User user = new User(userAux.getUsername(), R.mipmap.ic_perfil_round, idPelicula + "╩" + userAux.getRating(idPelicula));
                userRatingMovie.add(user);
            }
        }

        // Obtener el Recycler
        RecyclerView recycler = findViewById(R.id.rating_list_recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        RecyclerView.Adapter adapter = new RatingAdapter(userRatingMovie, idPelicula);
        recycler.setAdapter(adapter);

        RatingBar ratingBar = findViewById(R.id.rating_average);
        Movie movie = movieList.get(idPelicula);
        ratingBar.setRating(movie.getAverageRating());
        ratingBar.setEnabled(false);
    }
}
