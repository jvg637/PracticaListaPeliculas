package org.upv.movie.list.netflix.model;

import java.util.ArrayList;

/**
 * Created by Lionel on 07/11/2017.
 */

public class Lista {

    private String usuario;
    private String titulo;
    private String descripcion;
    private int icono;
    private ArrayList<Integer> peliculas;

    public Lista() {
        this.usuario = "";
        this.titulo = "";
        this.descripcion = "";
        this.icono = 0;
        peliculas = new ArrayList<>();
    }

    public Lista(String usuario, String titulo, String descripcion, int icono, ArrayList<Integer> peliculas) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.icono = icono;
        this.peliculas = peliculas;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int imagen) {
        this.icono = imagen;
    }

    public ArrayList<Integer> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Integer> peliculas) {
        this.peliculas = peliculas;
    }
}
