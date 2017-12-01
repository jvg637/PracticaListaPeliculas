package org.upv.movie.list.netflix.activity;

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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.upv.movie.list.netflix.model.Lista;
import org.upv.movie.list.netflix.adapters.ListaAdapter;
import org.upv.movie.list.netflix.utils.ListasVector;
import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.adapters.RecyclerItemClickListener;
import org.upv.movie.list.netflix.model.User;

import java.util.Iterator;
import java.util.Set;

import static org.upv.movie.list.netflix.activity.PerfilActivity.USERS;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USERS_KEY_USERS;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES;
import static org.upv.movie.list.netflix.activity.PerfilActivity.USER_LOGIN_PREFERENCES_KEY_USER;

/**
 * Created by Lionel on 07/11/2017.
 */

public class ListasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ACTUALIZAR_PERFIL = 10000;

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

        RecyclerView recycler = findViewById(R.id.recycler);

        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);

        recycler.setLayoutManager(lManager);

        RecyclerView.Adapter adapter = new ListaAdapter(listasVector);
        recycler.setAdapter(adapter);

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ListasActivity.this, MovieListActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ListasActivity.this).toBundle());
            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG).show();
            }
        });

        // Toolbar
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Navigation Drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setNavigationPerfil();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_1) {
            // Editar perfil
            Intent intent = new Intent(this, PerfilActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair<>(findViewById(R.id.navUserFoto), getString(R.string.shared_photo_perfil)));
            ActivityCompat.startActivityForResult(this, intent, ACTUALIZAR_PERFIL, options.toBundle());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setNavigationPerfil() {
        // Navigation drawer user/foto
        User user = readUserFromPreferences();
        String email = user.getMail();
        int photo = user.getDEFAULT_PHOTO();
        username = navigationView.getHeaderView(0).findViewById(R.id.navUsername);
        username.setText(email);
        userfoto = navigationView.getHeaderView(0).findViewById(R.id.navUserFoto);
        userfoto.setImageResource(photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTUALIZAR_PERFIL && resultCode == RESULT_OK) {
            User user = readUserFromPreferences();
            username = navigationView.getHeaderView(0).findViewById(R.id.navUsername);
            username.setText(user.getMail());

            userfoto = navigationView.getHeaderView(0).findViewById(R.id.navUserFoto);
            userfoto.setImageResource(user.getDEFAULT_PHOTO());
        }
    }

    private User readUserFromPreferences() {
        User userAux;
        User user = null;
        SharedPreferences prefsLogin = getSharedPreferences(USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        String userLogged = prefsLogin.getString(USER_LOGIN_PREFERENCES_KEY_USER, "");
        SharedPreferences prefs = getSharedPreferences(USERS, Context.MODE_PRIVATE);
        Set userList = prefs.getStringSet(USERS_KEY_USERS, null);
        Gson gson = new Gson();
        Iterator<String> userListIterator = userList.iterator();
        while (userListIterator.hasNext()) {
            userAux = gson.fromJson(userListIterator.next(), User.class);
            if (userLogged.equals(userAux.getUsername())) {
                user = userAux;
                break;
            }
        }
        return user;
    }
}
