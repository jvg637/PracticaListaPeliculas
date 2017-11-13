package org.upv.practicalistapeliculas;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.upv.practicalistapeliculas.model.User;

import static android.accounts.AccountManager.KEY_PASSWORD;

/**
 * Created by Lionel on 07/11/2017.
 */

public class ListasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String NAME_USER_PREFERENCES = "user";
    private static final String DEFAULT_EMAIL = "usuario1@gmail.com";
    private static final String DEFAULT_PASSWORD = "usuario1";
    private static final String DEFAULT_NAME = "Usuario Garcia";
    private static final int DEFAULT_PHOTO = R.mipmap.ic_perfil;
    private static final String KEY_PASSWORD = "user";
    private static final String KEY_USER = "password";
    private static final String KEY_NAME = "password";
    private static final String KEY_FOTO = "photo";

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView username;
    private ImageView userfoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        // Inicializar los elementos (ejemplo)
        ListasVector listasVector = new ListasVector();
        listasVector.anyade(new Lista(R.drawable.ic_fav, "Favoritas", "Mis películas favoritas"));
        listasVector.anyade(new Lista(R.drawable.ic_star, "Mejor valoradas", "La películas con mejor valoración"));


        recycler = (RecyclerView) findViewById(R.id.recycler);

        lManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(lManager);

        adapter = new ListaAdapter(listasVector);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(ListasActivity.this, "items: " + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListasActivity.this, MovieListActivity.class);
//                startActivity(intent);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ListasActivity.this).toBundle());
            }
        }));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG).show();
            }
        });

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setNavigationPerfil();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.nav_1) {
            // Editar perfil
            Intent intent = new Intent(this, PerfilActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<View, String>(findViewById(R.id.navUserFoto), getString(R.string.shared_photo_perfil)));
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else if (id == R.id.nav_2) {
            // …
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationPerfil(){

        // Navigation drawer user/foto
        SharedPreferences sp = this.getSharedPreferences(NAME_USER_PREFERENCES, Context.MODE_PRIVATE);
        String email = sp.getString(KEY_USER, DEFAULT_EMAIL);
        int photo = sp.getInt(KEY_FOTO, DEFAULT_PHOTO);

        //String password = sp.getString(KEY_PASSWORD, DEFAULT_PASSWORD);
        //String name = sp.getString(KEY_NAME, DEFAULT_NAME);

        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navUsername);
        username.setText(email);

        userfoto = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.navUserFoto);
        userfoto.setImageResource(photo);
    }



}
