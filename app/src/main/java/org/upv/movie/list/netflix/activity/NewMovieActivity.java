package org.upv.movie.list.netflix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.adapters.MovieListAdapter;
import org.upv.movie.list.netflix.adapters.RecyclerItemClickListener;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.movie.MovieList;

import java.util.List;

/**
 * Created by Miguel Á. Núñez on 15/12/2017.
 */

public class NewMovieActivity extends AppCompatActivity {
    private List<Movie> movieList;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);

        movieList = MovieList.list;

        EditText inputSearch = findViewById(R.id.inputSearch);

        final RecyclerView recyclerView = findViewById(R.id.recycler_new_movie);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager lManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lManager);

        adapter = new MovieListAdapter(movieList);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                guardarPelicula(position);
            }
        }));

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                adapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    public void guardarPelicula(int position){
        Intent intent = new Intent();
        intent.putExtra("idPelicula", adapter.getMoviesFilter().get(position).getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
