package org.upv.movie.list.netflix.utils;


import android.content.Context;
import android.util.Log;

import org.upv.movie.list.netflix.model.Lista;
import org.upv.movie.list.netflix.model.Listas;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionel on 08/11/2017.
 */

public class ListasVector implements Listas {

    protected List<Lista> vectorListas;

    public ListasVector() {
        vectorListas = new ArrayList<Lista>();
    }

    public Lista elemento(int id) {
        return vectorListas.get(id);
    }

    public List<Lista> elementos(){return vectorListas;}

    public void anyade(Lista lista) {
        vectorListas.add(lista);
    }

    public int nueva() {
        Lista lista = new Lista();
        vectorListas.add(lista);
        return vectorListas.size() - 1;
    }

    public void borrar(int id) {
        vectorListas.remove(id);
    }

    public int tamanyo() {
        return vectorListas.size();
    }

    public void actualiza(int id, Lista lista) {
        vectorListas.set(id, lista);
    }

    public void guardar(Context context, String nombreFichero){

        FileOutputStream f = null;
        String txt = "";

        try {

            if(fileExists(context, nombreFichero) == true){
                context.deleteFile(nombreFichero);
            }

            f = context.openFileOutput(nombreFichero, Context.MODE_APPEND);

            for (Lista lista : vectorListas) {

                txt += lista.getUsuario() + ";" + lista.getIcono() + ";" + lista.getTitulo() + ";" + lista.getDescripcion() + ";";

                for (Integer pelicula : lista.getPeliculas()) {
                    txt += pelicula + ";";
                }
                txt += "\n";
            }

            f.write(txt.getBytes());
            f.close();
        } catch (Exception e) {
            Log.e("PracticaListaPeliculas", e.getMessage(), e);
        } finally {

        }
    }

    public void abrir(Context context, String nombreFichero){

        List<String> result = new ArrayList<String>();
        String[] items;

        try {
            FileInputStream f = context.openFileInput(nombreFichero);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(f));

            String linea;
            do {
                linea = entrada.readLine();

                if (linea != null) {
                    result.add(linea);
                }
            } while (linea != null);

            f.close();

            vectorListas.clear();

            for (String l : result) {

                items = l.split(";");

                int icono = Integer.parseInt(items[1]);

                ArrayList<Integer> pelis = new ArrayList<>();

                for (int i = 4; i < items.length; i++) {
                    pelis.add(Integer.parseInt(items[i]));
                }
                // usuario, titulo, descripcion, icono, lista peliculas
                anyade(new Lista(items[0], items[2], items[3], icono, pelis));
            }

        } catch (Exception e) {
            Log.e("PracticaListaPeliculas", e.getMessage(), e);
        }
    }

    public void delete(Context context, String nombreFichero){

        try {
            if(fileExists(context, nombreFichero) == true){
                context.deleteFile(nombreFichero);
            }
        } catch (Exception e) {
            Log.e("PracticaListaPeliculas", e.getMessage(), e);
        }
    }

    public boolean fileExists(Context context, String nombreFichero){
        String[] files = context.fileList();
        for (String file : files) {
            if (file.equals(nombreFichero)) {
                return true;
            }
        }
        return false;
    }
}
