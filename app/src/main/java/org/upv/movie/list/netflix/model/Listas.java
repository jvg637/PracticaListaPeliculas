package org.upv.movie.list.netflix.model;

import android.content.Context;

import java.util.List;

/**
 * Created by Lionel on 08/11/2017.
 */

public interface Listas {

    // Devuelve el elemento dado su id
    Lista elemento(int id);

    // Devuelve los elementos
    List<Lista> elementos();

    // Añade el elemento indicado
    void anyade(Lista lista);

    // Añade un nuevo elemento devuelve su id
    int nueva();

    // Elimina el elemento con el id indicado
    void borrar(int id);

    // Devuelve el número de elementos
    int tamanyo();

    // Reemplaza un elemento
    void actualiza(int id, Lista lista);

    // Guarda la lista en un fichero
    void guardar(Context context, String fichero);

    // Lee la lista desde un fichero
    void abrir(Context context, String fichero);
}
