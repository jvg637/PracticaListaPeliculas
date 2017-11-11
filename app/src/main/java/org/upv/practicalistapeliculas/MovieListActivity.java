package org.upv.practicalistapeliculas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.upv.practicalistapeliculas.adapters.MovieListAdapter;
import org.upv.practicalistapeliculas.model.Movie;
import org.upv.practicalistapeliculas.movie.MovieList;
import org.upv.practicalistapeliculas.movie.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MovieListActivity extends AppCompatActivity {

    /// RECYCLER ///
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        List<Movie> movieList = new ArrayList<>();
        if(movieList.isEmpty()) {
            String json = Utils.loadJSONFromResource(this, R.raw.movies);
            Gson gson = new Gson();
            Type collection = new TypeToken<ArrayList<Movie>>() {
            }.getType();
            MovieList.list = gson.fromJson(json, collection);
            movieList = MovieList.list;
        }

        // Obtener el Recycler
        recycler = findViewById(R.id.movie_list_recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MovieListAdapter(movieList);
        recycler.setAdapter(adapter);
    }

}
