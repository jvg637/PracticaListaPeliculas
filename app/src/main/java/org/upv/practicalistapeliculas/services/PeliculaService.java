package org.upv.practicalistapeliculas.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.upv.practicalistapeliculas.entities.Pelicula;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Miguel Á. Núñez on 08/11/2017.
 */

/* Esta clase nos va a permitir almacenar en el telefono las peliculas que creemos */
public class PeliculaService {
    /* Las dos siguientes lineas sirven para la obtencion de la pelicula y la imagen de esta */
    private static final String API_KEY = "002d6e703f93c707752464cb04eaca27";
    private static final String URL_IMAGE = "http://image.tmdb.org/t/p/original";

    /* Lista de peliculas que tiene la app */
    private List<Pelicula> peliculas;

    /* En el constructor inicializamos la lista */
    public PeliculaService(){
        peliculas = new ArrayList<Pelicula>();
    }

    public int addPelicula(Pelicula pelicula) {
        /*if(peliculas.isEmpty()) {
            //Lectura de las peliculas de la memoria del dispositivo
            //movies = movieRepository.findAll();
        }*/
        List<String> actors = new ArrayList<String>(), directors = new ArrayList<String>();
        String uri = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY
                + "&language=es-ES&query=" + pelicula.getNombre().replaceAll(" ","+") + "&page=1";
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            InputStream json = connection.getInputStream();

            /* Investigando el parser a JSON */
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(json, "UTF-8"));
            //pelicula.setNombre(null);

            /* Damos valores a la pelicula */
            pelicula.setIdPelicula((String) jsonObject.get("id"));
            pelicula.setNombre((String) jsonObject.get("title"));
            pelicula.setPortada((String) jsonObject.get("poster_path"));
            pelicula.setValoracion((String) jsonObject.get("vote_average"));
            pelicula.setDescripcion((String) jsonObject.get("overview"));
            String[] year = ((String) jsonObject.get("release_date")).split("-");
            pelicula.setYear(year[0]);

            /* Se cierra la conexion establecida */
            connection.disconnect();

            /* Faltan actores y director */

            /* Llamada a la api para que nos devuelva la info de una pelicula*/
            uri = "https://api.themoviedb.org/3/movie/" + pelicula.getIdPelicula()
                    + "/credits?api_key=" + API_KEY;
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            json = connection.getInputStream();
            jsonParser = new JSONParser();
            jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(json, "UTF-8"));

            JSONArray actores = (JSONArray) jsonObject.get("cast");

            Iterator<JSONArray> it = actores.iterator();
            while (it.hasNext()) {
                JSONArray act = it.next();
                actors.add((String) act.get(5));
            }

            JSONArray directores = (JSONArray) jsonObject.get("crew");

            it = directores.iterator();
            while (it.hasNext()) {
                JSONArray dir = it.next();
                directors.add((String) dir.get(5));
            }

            connection.disconnect();

            pelicula.setActors(actors);
            pelicula.setDirectors(directors);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        peliculas.add(pelicula);
        return 0;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
}
