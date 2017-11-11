package org.upv.practicalistapeliculas.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.upv.practicalistapeliculas.bbdd.TablasBBDD;

/**
 * Created by Miguel Á. Núñez on 11/11/2017.
 */

public class PeliculaDBAdapter {
    /*
     * Se define una constante con el nombre de la tabla
     */
    private static final String TABLA_PELICULA = "Peliculas";
    private static final String TABLA_ACTOR = "Actores";
    private static final String TABLA_DIRECTOR = "Directores";

    /*
     * Se definen constantes con el nombre de las columnas de las tablas
     */
    private static final String P_COLUMNA_ID = "idPelicula";
    private static final String P_COLUMNA_NOMBRE = "nombre";
    private static final String P_COLUMNA_URL = "url";
    private static final String P_COLUMNA_DESC = "descripcion";
    private static final String P_COLUMNA_YEAR = "year";
    private static final String P_COLUMNA_PORTADA = "portada";
    private static final String P_COLUMNA_VALORACION = "valoracion";
    private static final String ACT_COLUMNA_NOMBRE = "nombre";
    private static final String DIR_COLUMNA_NOMBRE = "nombre";

    /*
     * Se define lista de columnas de la tabla para las consulta
     */
    private String[] columnas = new String[] {P_COLUMNA_ID,
                                              P_COLUMNA_NOMBRE,
                                              P_COLUMNA_URL,
                                              P_COLUMNA_DESC,
                                              P_COLUMNA_YEAR,
                                              P_COLUMNA_PORTADA,
                                              P_COLUMNA_VALORACION};

    private String[] columnasACT = new String[] {ACT_COLUMNA_NOMBRE};
    private String[] columnasDIR = new String[] {DIR_COLUMNA_NOMBRE};

    private Context contexto;
    private TablasBBDD dbHelper;
    private SQLiteDatabase db;

    public PeliculaDBAdapter(Context context) {
        this.contexto = context;
    }

    public PeliculaDBAdapter abrir() throws SQLException
    {
        dbHelper = new TablasBBDD(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbHelper.close();
    }

    /**
     * Devuelve cursor con todos las columnas de la tabla Peliculas
     */
    public Cursor getCursorPeliculas() throws SQLException
    {
        Cursor c = db.query( true, TABLA_PELICULA, columnas, null, null, null, null, null, null);
        return c;
    }

    /**
     * Devuelve cursor con todos las columnas de la tabla Actores
     */
    public Cursor getCursorActores() throws SQLException
    {
        Cursor c = db.query( true, TABLA_ACTOR, columnasACT, null, null, null, null, null, null);
        return c;
    }

    /**
     * Devuelve cursor con todos las columnas de la tabla Peliculas
     */
    public Cursor getCursorDirectores() throws SQLException
    {
        Cursor c = db.query( true, TABLA_DIRECTOR, columnasDIR, null, null, null, null, null, null);
        return c;
    }
}
