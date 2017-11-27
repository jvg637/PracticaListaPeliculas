package org.upv.practicalistapeliculas.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;

import org.upv.practicalistapeliculas.R;
import org.upv.practicalistapeliculas.adapters.RatingAdapter;
import org.upv.practicalistapeliculas.model.Movie;
import org.upv.practicalistapeliculas.model.User;
import org.upv.practicalistapeliculas.movie.MovieList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ratings extends AppCompatActivity {

    private SharedPreferences prefs;
    private Set userList = new HashSet<User>();
    private RatingBar ratingBar;
    private List<Movie> movieList;

    /// RECYCLER ///
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        movieList = MovieList.list;

        int idPelicula = getIntent().getExtras().getInt("idPelicula");

        prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);
        userList = prefs.getStringSet("users", userList );

        // Obtener el Recycler
        recycler = findViewById(R.id.rating_list_recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RatingAdapter(userList, idPelicula);
        recycler.setAdapter(adapter);

        ratingBar = findViewById(R.id.rating_average);
        Movie movie = movieList.get(idPelicula);
        ratingBar.setRating(movie.getAverageRating());
    }
}
