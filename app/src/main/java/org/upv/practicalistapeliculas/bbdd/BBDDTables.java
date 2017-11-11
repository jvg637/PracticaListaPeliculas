package org.upv.practicalistapeliculas.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Miguel Á. Núñez on 10/11/2017.
 */

public class BBDDTables extends SQLiteOpenHelper {

    private static int version = 1;
    private static String name = "PeliculasDb" ;
    private static SQLiteDatabase.CursorFactory factory = null;

    //Sentencia SQL para crear la tabla de Usuarios
    private static String sqlPeliculasCreate = "CREATE TABLE Peliculas (idPelicula TEXT NOT NULL, " +
            "nombre TEXT, " +
            "url TEXT, " +
            "descripcion TEXT, " +
            "year TEXT, " +
            "portada TEXT, " +
            "valoracion TEXT)";

    //Sentencia SQL para crear la tabla de Actores
    private static String sqlActoresCreate = "CREATE TABLE Actores (_id INTEGER PRIMARY KEY," +
            "nombre TEXT" +
            "idPelicula TEXT," +
            "FOREIGN KEY(idPelicula) REFERENCES Peliculas(idPelicula))";

    //Sentencia SQL para crear la tablea de Directores
    private static String sqlDirectoresCreate = "CREATE TABLE Directores (_id INTEGER PRIMARY KEY," +
            "nombre TEXT" +
            "idPelicula TEXT," +
            "FOREIGN KEY(idPelicula) REFERENCES Peliculas(idPelicula))";

    public BBDDTables(Context context)
    {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlPeliculasCreate);
        db.execSQL(sqlActoresCreate);
        db.execSQL(sqlDirectoresCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
