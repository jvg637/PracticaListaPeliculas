package org.upv.lista.list.netflix.activity;

import android.content.Intent;
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

import org.upv.lista.list.R;
import org.upv.lista.list.netflix.adapters.RecyclerItemClickListener;
import org.upv.lista.list.netflix.adapters.MovieListAdapter;
import org.upv.lista.list.netflix.model.Movie;
import org.upv.lista.list.netflix.movie.MovieList;
import org.upv.lista.list.netflix.movie.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MovieListActivity extends AppCompatActivity {

    /// RECYCLER ///
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

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
        if (movieList.isEmpty()) {
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

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(MovieListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MovieListActivity.this, ShowEditMovieActivity.class);
//                intent.putExtra("ID", movieList.get(position).getId());
//                startActivity(intent);

                intent.putExtra(ShowEditMovieActivity.PARAM_EXTRA_ID_PELICULA, (int)movieList.get(position).getId());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MovieListActivity.this, new Pair<View, String>(v.findViewById(R.id.movie_poster), getString(R.string.shared_photo_list_movie)));
                ActivityCompat.startActivity(MovieListActivity.this, intent, options.toBundle());
            }
        }));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter = new MovieListAdapter(movieList);
        recycler.setAdapter(adapter);
    }
}
