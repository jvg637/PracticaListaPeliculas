package org.upv.practicalistapeliculas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import org.upv.practicalistapeliculas.R;
import org.upv.practicalistapeliculas.utils.DownloadImageTask;
import org.upv.practicalistapeliculas.model.Movie;
import org.upv.practicalistapeliculas.movie.MovieList;

/**
 * Created by jvg63 on 09/11/2017.
 */

public class ShowEditMovieActivity extends AppCompatActivity {
    public static String PARAM_EXTRA_ID_PELICULA = "ID";

//    id
//    Título
//    Genero
//    Año de Estreno
//    Imagen:
//    Sinopsis
//    Puntuación
// Studio

    private ImageView photo;
    private EditText title;
    private EditText category;
    private EditText summary;
    private EditText directors;
    private EditText actors;
    private EditText producers;
    private EditText studio;
    private RatingBar rating;
    private FloatingActionButton btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edit_movie);

        photo = findViewById(R.id.photo);
        title = findViewById(R.id.title);
        category = findViewById(R.id.category);
        summary = findViewById(R.id.summary);
        actors = findViewById(R.id.actor);
        directors = findViewById(R.id.director);
        producers = findViewById(R.id.producer);
        studio = findViewById(R.id.studio);
        rating = findViewById(R.id.ratingBar);

        btnSave = findViewById(R.id.fab);

        Intent data = getIntent();
        int id = -1;

        if (data != null && data.getExtras() != null) {
            id = data.getExtras().getInt(PARAM_EXTRA_ID_PELICULA, -1);
        }

        if (id == -1) {
            // Mode Edit
        } else {
//            leerDatos();
            // Mode View
            postponeEnterTransition();
            mostrarPelicula(id);
        }
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }


    private void mostrarPelicula(int id) {
        Movie movie = MovieList.list.get(id);

        title.setText(movie.getTitle());
        category.setText(movie.getCategory());
        summary.setText(movie.getDescription());
        studio.setText(movie.getStudio());
        rating.setRating(2.5f);
        directors.setText(movie.getDirectors());
        actors.setText(movie.getActors());
        producers.setText(movie.getProducers());


        new DownloadImageTask(photo).execute(movie.getBackgroundImageUrl());
        protectFields();

        Transition lista_enter = TransitionInflater.from(this)
                .inflateTransition(R.transition.transition_curva);
        getWindow().setSharedElementEnterTransition(lista_enter);
        scheduleStartPostponedTransition(photo);

    }

    private void protectFields() {
//        photo.setEnabled(false);
        photo.setFocusable(false);

//        title.setEnabled(false);
        title.setFocusable(false);

//        studio.setEnabled(false);
        studio.setFocusable(false);

//        category.setEnabled(false);
        category.setFocusable(false);

//        summary.setEnabled(false);
        summary.setFocusable(false);

//        actor.setEnabled(false);
        actors.setFocusable(false);

//        director.setEnabled(false);
        directors.setFocusable(false);

//        producer.setEnabled(false);
        producers.setFocusable(false);

        rating.setEnabled(false);

        btnSave.setVisibility(View.GONE);
    }

//    private void leerDatos() {
//        if (MovieList.list != null)
//            MovieList.list.clear();
//        else
//            MovieList.list = new ArrayList<>();
//
//        String json = Utils.loadJSONFromResource(this, R.raw.movies);
//        Gson gson = new Gson();
//        Type collection = new TypeToken<ArrayList<Movie>>() {
//        }.getType();
//        MovieList.list = gson.fromJson(json, collection);
//    }

}
