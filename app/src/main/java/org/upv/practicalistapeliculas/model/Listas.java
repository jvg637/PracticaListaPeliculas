package org.upv.practicalistapeliculas.model;

import org.upv.practicalistapeliculas.model.Lista;

/**
 * Created by Lionel on 08/11/2017.
 */

public interface Listas {

    // Devuelve el elemento dado su id
    Lista elemento(int id);

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
}
