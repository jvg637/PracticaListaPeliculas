package org.upv.movie.list.netflix.model;

import android.annotation.SuppressLint;

import org.upv.movie.list.netflix.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 10/11/17.
 */

public class User implements Serializable {
    private String mail;
    private String username;
    private String password;
    private String fullname;
    private int DEFAULT_PHOTO = R.mipmap.ic_perfil;
    private List<String> listRatings;


    @SuppressLint("UseSparseArrays")
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        listRatings = new ArrayList<>();
    }

    @SuppressLint("UseSparseArrays")
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.mail = email;
        listRatings = new ArrayList<>();
    }

    @SuppressLint("UseSparseArrays")
    public User(String username, String password, String email, String fullname) {
        this.username = username;
        this.password = password;
        this.mail = email;
        this.fullname = fullname;
        listRatings = new ArrayList<>();
    }

    @SuppressLint("UseSparseArrays")
    public User(String username, int photo, String commentRating) {
        this.username = username;
        this.DEFAULT_PHOTO = photo;
        listRatings = new ArrayList<>();
        listRatings.add(commentRating);
    }

    public int getDEFAULT_PHOTO() {
        return DEFAULT_PHOTO;
    }

    public void setDEFAULT_PHOTO(int DEFAULT_PHOTO) {
        this.DEFAULT_PHOTO = DEFAULT_PHOTO;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRating(long idPelicula) {
        String ret = "0.0f╩ ";
        if (listRatings != null && !listRatings.isEmpty()) {
            for (String str : listRatings) {
                String[] rating = str.split("╩");
                if (Long.valueOf(rating[0]) == idPelicula) {
                    ret = rating[1] + "╩" + rating[2];
                    break;
                }
            }
        }
        return ret;
    }

    public void setRating(long idPelicula, float rating, String comment) {
        String ratingComment = idPelicula + "╩" + rating + "╩" + comment;
        if (listRatings == null) {
            listRatings = new ArrayList<>();
        }
        listRatings.add(ratingComment);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;

        return password != null ? password.equals(user.password) : user.password == null;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
