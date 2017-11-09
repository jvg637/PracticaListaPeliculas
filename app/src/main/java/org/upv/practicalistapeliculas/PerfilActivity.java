package org.upv.practicalistapeliculas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PerfilActivity extends AppCompatActivity {


    private static final String NAME_USER_PREFERENCES = "user";
    private static final String DEFAULT_EMAIL = "usuario1@gmail.com";
    private static final String DEFAULT_PASSWORD = "usuario1";
    private static final String DEFAULT_NAME = "Usuario Garcia";
    private static final int DEFAULT_PHOTO = R.mipmap.ic_perfil;
    private static final String KEY_PASSWORD = "user";
    private static final String KEY_USER = "password";
    private static final String KEY_NAME = "password";
    private static final String KEY_FOTO = "photo";


    private EditText contraseña;
    private EditText usuario;
    private EditText name;
    private ImageView photo;
    private CheckBox mostrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        usuario = (EditText) findViewById(R.id.usuario);
        contraseña = (EditText) findViewById(R.id.contraseña);
        name = (EditText) findViewById(R.id.name);
        photo = (ImageView) findViewById(R.id.photo);

        mostrar = (CheckBox) findViewById(R.id.mostrar_contraseña);

        User user = readUserFromPreferences();
        usuario.setText(user.getEmail());
        contraseña.setText(user.getPassword());
        name.setText(user.getName());
        photo.setImageResource(user.getFoto());
//        contraseña.setText(user.getPassword());

        Transition lista_enter = TransitionInflater.from(this)
                .inflateTransition(R.transition.transition_pefil_enter);
        getWindow().setEnterTransition(lista_enter);

    }

    public User readUserFromPreferences() {
        SharedPreferences sp = this.getSharedPreferences(NAME_USER_PREFERENCES, Context.MODE_PRIVATE);
        String email = sp.getString(KEY_USER, DEFAULT_EMAIL);
        String password = sp.getString(KEY_PASSWORD, DEFAULT_PASSWORD);
        String name = sp.getString(KEY_NAME, DEFAULT_NAME);
        int photo = sp.getInt(KEY_FOTO, DEFAULT_PHOTO);

        return new User(email, name, password, photo);
    }

    public void saveUserPreferences() {
        SharedPreferences sp = this.getSharedPreferences(NAME_USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(KEY_USER, usuario.getText().toString());
        editor.putString(KEY_PASSWORD, contraseña.getText().toString());
        editor.putString(KEY_NAME, name.getText().toString());
        editor.putInt(KEY_FOTO, R.mipmap.ic_perfil);
        editor.commit();
    }

    public void mostrarContraseña(View v) {

        if (mostrar.isChecked()) {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }


    public void saveUser(View view) {

        if (usuario.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()
                || name.getText().toString().isEmpty()) {


            Snackbar.make(findViewById(R.id.container), "Introduza todos los campos", Snackbar.LENGTH_LONG)
                    .show();
        } else {
            saveUserPreferences();

            finish();
        }

    }

}
