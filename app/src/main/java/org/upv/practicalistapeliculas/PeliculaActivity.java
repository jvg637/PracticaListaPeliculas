package org.upv.practicalistapeliculas;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.upv.practicalistapeliculas.bbdd.TablasBBDD;

public class PeliculaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);

        /*
         * Declaramos el controlador de la BBDD y accedemos en modo escritura
         */
        TablasBBDD dbHelper = new TablasBBDD(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


    }
}
