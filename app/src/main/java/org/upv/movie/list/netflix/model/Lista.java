package org.upv.movie.list.netflix.model;

/**
 * Created by Lionel on 07/11/2017.
 */

public class Lista {

    private String titulo;
    private String descripcion;
    private int imagen;

    public Lista() {
        this.titulo = "";
        this.descripcion = "";
        this.imagen = 0;
    }

    public Lista(int imagen, String titulo, String descripcion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
