package org.upv.movie.list.netflix.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.adapters.RecyclerItemClickListener;
import org.upv.movie.list.netflix.adapters.MovieListAdapter;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.movie.MovieList;
import org.upv.movie.list.netflix.movie.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MovieListActivity extends AppCompatActivity {

    //Lista peliculas
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        /*
         * Posiblemente esto no haya que hacerlo mas adelante, de momento es para evitar un NullPointerException
         */
        movieList = new ArrayList<>();
        Gson gson = new Gson();
        if (movieList.isEmpty()) {
            String json = Utils.loadJSONFromResource(this, R.raw.movies);
            Type collection = new TypeToken<ArrayList<Movie>>() {
            }.getType();
            MovieList.list = gson.fromJson(json, collection);
            movieList = MovieList.list;
        }

        //Leo de preferencias las valoraciones de todas las peliculas
        SharedPreferences prefs = getSharedPreferences("Valoraciones", Context.MODE_PRIVATE);
        Set movieRatings = new HashSet<String>();
        movieRatings = prefs.getStringSet("ratings", movieRatings);
        gson = new Gson();
        for (String movieRating : (Iterable<String>) movieRatings) {
            String[] rating = gson.fromJson(movieRating, String.class).split("╩");
            for (Movie movie : movieList) {
                if (movie.getId() == Long.valueOf(rating[0])) {
                    movie.addRating(Float.parseFloat(rating[1]));
                    break;
                }
            }
        }

        // Obtener el Recycler
        RecyclerView recycler = findViewById(R.id.movie_list_recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        RecyclerView.Adapter adapter = new MovieListAdapter();
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(MovieListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MovieListActivity.this, ShowEditMovieActivity.class);
                intent.putExtra(ShowEditMovieActivity.PARAM_EXTRA_ID_PELICULA, (int) movieList.get(position).getId());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MovieListActivity.this, new Pair<View, String>(v.findViewById(R.id.movie_poster), getString(R.string.shared_photo_list_movie)));
                ActivityCompat.startActivity(MovieListActivity.this, intent, options.toBundle());
            }
        }));
    }

}
