package org.upv.practicalistapeliculas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionel on 07/11/2017.
 */

public class ListasActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private FloatingActionButton fab;

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
                Toast.makeText(ListasActivity.this, "items: " + position, Toast.LENGTH_SHORT).show();
            }
        }));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Se presionó el FAB", Snackbar.LENGTH_LONG) .show();
            }
        });

    }
}
