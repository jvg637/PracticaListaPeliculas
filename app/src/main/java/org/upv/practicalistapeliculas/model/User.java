package org.upv.practicalistapeliculas.model;

import android.util.Log;

import org.upv.practicalistapeliculas.R;

import java.io.Serializable;

/**
 * Created by Ivan on 10/11/17.
 */

public class User implements Serializable {
    private String mail;
    private String username;
    private String password;
    private int DEFAULT_PHOTO = R.mipmap.ic_perfil;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.mail = email;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        Log.d("x", "Comprobando " + ((User) o).getUsername() + " con " + this.getUsername() );

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;

        return password != null ? password.equals(user.password) : user.password == null;
    }

}
