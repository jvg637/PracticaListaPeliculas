package org.upv.practicalistapeliculas;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.upv.practicalistapeliculas.bbdd.BBDDTables;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        /*
         * Declaramos el controlador de la BBDD y accedemos en modo escritura
         */
        /*BBDDTables dbHelper = new BBDDTables(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();*/


    }
}
