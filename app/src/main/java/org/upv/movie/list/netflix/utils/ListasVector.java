package org.upv.movie.list.netflix.utils;

import org.upv.movie.list.netflix.model.Lista;
import org.upv.movie.list.netflix.model.Listas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionel on 08/11/2017.
 */

public class ListasVector implements Listas {

    protected List<Lista> vectorListas;

    public ListasVector(){
        vectorListas = new ArrayList<Lista>();
    };

    public Lista elemento(int id){
        return vectorListas.get(id);
    }

    public void anyade(Lista lista){
        vectorListas.add(lista);
    }

    public int nueva(){
        Lista lista = new Lista();
        vectorListas.add(lista);
        return vectorListas.size() - 1;
    }

    public void borrar(int id){
        vectorListas.remove(id);
    }

    public int tamanyo(){
        return vectorListas.size();
    }

    public void actualiza(int id, Lista lista){
        vectorListas.set(id, lista);
    }
}
