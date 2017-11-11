package org.upv.practicalistapeliculas;

/**
 * Created by jvg63 on 09/11/2017.
 */

public class User {
    public String email;
    public String name;
    public String password;
    public int foto = R.mipmap.ic_perfil_round;

    public User(String email, String login, String password, int foto) {
        this.email = email;
        this.name = login;
        this.password = password;
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
