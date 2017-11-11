package org.upv.practicalistapeliculas.entities;

import java.util.List;

/**
 * Created by Miguel Á. Núñez on 08/11/2017.
 */

public class Pelicula {
    private String idPelicula;
    private String nombre;
    private String url;
    private String descripcion;
    private String year;
    private List<String> directors;
    private List<String> actors;
    private String portada;
    private String valoracion;

    public Pelicula() {}

    /* Constructor Pelicula al que le pasamos un nombre y un enlace al trailer */
    public Pelicula(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
