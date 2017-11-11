package org.upv.practicalistapeliculas.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.upv.practicalistapeliculas.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jvg63 on 09/11/2017.
 */

public class ShowEditMovie extends AppCompatActivity {
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
    private EditText director;
    private EditText producer;
    private EditText studio;
    private RatingBar rating;
    private FloatingActionButton btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_edit_movie);

        photo = (ImageView) findViewById(R.id.photo);
        title  = (EditText) findViewById(R.id.title);
        category = (EditText) findViewById(R.id.category);
        summary = (EditText) findViewById(R.id.summary);
        rating = (RatingBar)  findViewById(R.id.ratingBar);;
        director = (EditText) findViewById(R.id.director);
        producer = (EditText) findViewById(R.id.producer);
        studio = (EditText) findViewById(R.id.studio);

        btnSave = (FloatingActionButton) findViewById(R.id.fab);

        Intent data = getIntent();
        int id = -1;

        if (data != null && data.getExtras() != null) {
            id = data.getExtras().getInt(PARAM_EXTRA_ID_PELICULA, -1);
        }
        
        if (id == -1) {
            // Mode Edit
        } else {
            leerDatos();
            // Mode View
            mostrarPelicula(id);
        }
    }

    private void mostrarPelicula(int id) {
        Movie movie = MovieList.list.get(id);

        photo.setImageResource(R.mipmap.ic_perfil);
        title.setText(movie.getTitle() + " ( 2010 )");
        category.setText(movie.getCategory());
        summary.setText(movie.getDescription());
        studio.setText(movie.getStudio());
        director.setText("Director 1");
        producer.setText("Productor 1");;
        rating.setRating(2.5f);

        protectFields();
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

//        director.setEnabled(false);
        director.setFocusable(false);

//        producer.setEnabled(false);
        producer.setFocusable(false);

        rating.setEnabled(false);

        btnSave.setVisibility(View.GONE);
    }

    private void leerDatos() {
        if (MovieList.list != null)
            MovieList.list.clear();
        else
            MovieList.list = new ArrayList<>();

        String json = Utils.loadJSONFromResource(this, R.raw.movies);
        Gson gson = new Gson();
        Type collection = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        MovieList.list = gson.fromJson(json, collection);
    }
}
