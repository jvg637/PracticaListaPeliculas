package org.upv.movie.list.netflix.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.adapters.RecyclerItemClickListener;
import org.upv.movie.list.netflix.adapters.MovieListAdapter;
import org.upv.movie.list.netflix.adapters.RecyclerViewTouchListener;
import org.upv.movie.list.netflix.model.Lista;
import org.upv.movie.list.netflix.model.Movie;
import org.upv.movie.list.netflix.model.User;
import org.upv.movie.list.netflix.movie.MovieList;
import org.upv.movie.list.netflix.movie.Utils;
import org.upv.movie.list.netflix.utils.ListasVector;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES_KEY_USER;


public class MovieListActivity extends AppCompatActivity {

    private static final int REFRESH_RATINGS = 10000;
    private static final int ADD_MOVIE = 10001;
    private static String FICHERO_LISTAS = "listas.txt";
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private RecyclerView recycler;
    //Lista peliculas
    private List<Movie> movieList;

    //Indicador de archivo de idioma
    private int archivoMovie;

    //Vectores de peliculas usuarios
    private ArrayList<Integer> idsPeliculasUser;
    private String tituloLista, descLista;
    private int iconoLista;
    private String userLista;
    private ListasVector listaPeliculasTodosUsuarios;
    private String userLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //Detectamos el idioma que tiene el dispositivo
        String language = Locale.getDefault().getLanguage();

        //Obtenemos el usuario conectado
        SharedPreferences prefsLogin = getSharedPreferences(USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        userLogged = prefsLogin.getString(USER_LOGIN_PREFERENCES_KEY_USER, "");

        fab = findViewById(R.id.fab_add_movie);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewMovieActivity.class);
                startActivityForResult(intent, ADD_MOVIE);
            }
        });

        //Cargamos el archivo movies que corresponda con el idioma
        switch (language) {
            case "es":
                archivoMovie = R.raw.movies_es;
                break;
            case "fr":
                archivoMovie = R.raw.movies_fr;
                break;
            default:
                archivoMovie = R.raw.movies;
                break;
        }


        tituloLista = getIntent().getExtras().getString("tituloLista");
        listaPeliculasTodosUsuarios = new ListasVector();
        listaPeliculasTodosUsuarios.abrir(this, FICHERO_LISTAS);

        Gson gson = new Gson();
        //Cargamos las peliculas en MovieList
        movieList = new ArrayList<>();
        String json = Utils.loadJSONFromResource(this, archivoMovie);
        Type collection = new TypeToken<ArrayList<Movie>>() {
        }.getType();
        MovieList.list = gson.fromJson(json, collection);

        if (tituloLista.equals("todas")) {
            movieList = MovieList.list;
            fab.setVisibility(View.GONE);
            mState =true;
            invalidateOptionsMenu();

        } else {
            //Aqui cargamos las listas de todos los usuarios y despues la del usuario que está con la sesión iniciada
            idsPeliculasUser = (ArrayList<Integer>) getIntent().getExtras().get("peliculasLista");
            descLista = getIntent().getExtras().getString("descLista");
            iconoLista = getIntent().getExtras().getInt("iconoLista");
            userLista = getIntent().getExtras().getString("userLista");
            for (Movie movie : MovieList.list) {
                for (int i = 0; i < idsPeliculasUser.size(); i++) {
                    if (idsPeliculasUser.get(i) == movie.getId()) {
                        movieList.add(movie);
                    }
                }
            }
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
        recycler = findViewById(R.id.movie_list_recycler);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MovieListAdapter(movieList);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recycler, new RecyclerViewTouchListener.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MovieListActivity.this, ShowEditMovieActivity.class);
                intent.putExtra(ShowEditMovieActivity.PARAM_EXTRA_ID_PELICULA, (int) movieList.get(position).getId());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MovieListActivity.this,
                        new Pair<View, String>(v.findViewById(R.id.movie_poster), getString(R.string.shared_photo_list_movie)));
                ActivityCompat.startActivityForResult(MovieListActivity.this, intent, REFRESH_RATINGS, options.toBundle());
            }


            @Override
            public void onLongClick(View view, int position) {
                // No se puede eliminar la lista por defecto
                deleteListDialog(movieList, position);
            }
        }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_MOVIE && resultCode == RESULT_OK) {
            long idPelicula = data.getExtras().getLong("idPelicula");
            idsPeliculasUser.add((int) idPelicula);

            //Me falta averiguar como añadir en el fichero los datos que he modificado
            for (int i = 0; i < listaPeliculasTodosUsuarios.tamanyo(); i++) {
                if (listaPeliculasTodosUsuarios.elemento(i).getUsuario().equals(userLogged)
                        && listaPeliculasTodosUsuarios.elemento(i).getTitulo().equals(tituloLista)
                        && listaPeliculasTodosUsuarios.elemento(i).getDescripcion().equals(descLista)
                        && listaPeliculasTodosUsuarios.elemento(i).getIcono() == iconoLista) {
                    Lista peliculaAdd = new Lista(userLista, tituloLista, descLista, iconoLista, idsPeliculasUser);
                    listaPeliculasTodosUsuarios.actualiza(i, peliculaAdd);
                }
            }
            movieList.add(MovieList.list.get((int) idPelicula));
            //listaPeliculasTodosUsuarios.anyade(new Lista(user.getUsername(), titulo, descripcion, icono, new ArrayList<Integer>()));
            listaPeliculasTodosUsuarios.guardar(this, FICHERO_LISTAS);
            adapter = new MovieListAdapter(movieList);
            recycler.setAdapter(adapter);

        }
        if (resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
            invalidateOptionsMenu();
        }
    }

    private void deleteListDialog(final List<Movie> movies, int position) {

        final int pos = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.LA_delete_list_dialog) + movies.get(pos).getTitle() + "\" ?");

        // OK button
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                movies.remove(pos);
                adapter = new MovieListAdapter(movieList);
                recycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        // Cancel button
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    boolean mState = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_movie, menu);


        if (mState)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }

        if (movieList.size()<=0)
            menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intentData = new Intent();
            intentData.putExtra("ACTION", "EDIT");
            setResult(RESULT_OK, intentData);
            finish();

        } else if (id == R.id.action_delete) {
            Intent intentData = new Intent();
            intentData.putExtra("ACTION", "REMOVE");
            setResult(RESULT_OK, intentData);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
