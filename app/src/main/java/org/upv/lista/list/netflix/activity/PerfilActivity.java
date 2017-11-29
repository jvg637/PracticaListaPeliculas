package org.upv.lista.list.netflix.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.upv.lista.list.R;
import org.upv.lista.list.netflix.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PerfilActivity extends AppCompatActivity {

    // Datos del usuario que ha hecho login
    public static final String USER_LOGIN_PREFERENCES = "user";
    public static final String USER_LOGIN_PREFERENCES_KEY_USER = "user";

    // Fichero de usuarios
    public static final String USERS = "Usuarios";
    public static final String USERS_KEY_USERS = "users";

    private static final String DEFAULT_EMAIL = "usuario1@gmail.com";
    private static final String DEFAULT_USER = "usuario1";
    private static final String DEFAULT_PASSWORD = "usuario1";
    private static final String DEFAULT_NAME = "Usuario Garcia";


    private EditText usuario;
    private EditText contraseña;
    private EditText email;
    private EditText name;
    private ImageView photo;
    private CheckBox mostrar;

    private Set<String> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        photo = findViewById(R.id.photo);

        mostrar = findViewById(R.id.mostrar_contraseña);

        User user = readUserFromPreferences();

        if (user != null) {
            usuario.setText(user.getUsername());
            contraseña.setText(user.getPassword());
            name.setText("Nombre de Usuario1");
            email.setText(user.getMail());
            photo.setImageResource(user.getDEFAULT_PHOTO());
//        contraseña.setText(user.getPassword());
        } else {
            Snackbar.make(findViewById(R.id.container), "Usuario no ha hecho login!", Snackbar.LENGTH_LONG).show();
        }

        Transition lista_enter = TransitionInflater.from(this)
                .inflateTransition(R.transition.transition_pefil_enter);
        Transition curve_shared = TransitionInflater.from(this)
                .inflateTransition(R.transition.transition_curva);
        getWindow().setEnterTransition(lista_enter);
        getWindow().setSharedElementEnterTransition(curve_shared);

        postponeEnterTransition();

        scheduleStartPostponedTransition(photo);
    }


    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    public void saveUserPreferences() {
        User newUser = new User(usuario.getText().toString(), contraseña.getText().toString(), email.getText().toString(), name.getText().toString());


        SharedPreferences prefs = getSharedPreferences(USERS, Context.MODE_PRIVATE);

        Set userList = prefs.getStringSet(USERS_KEY_USERS, null);

        Set  userListNew = new HashSet<>();

        if (userList == null) {
            userList = new HashSet<>();
        }

        Gson gson = new Gson();

        Iterator<String> userListIterator = userList.iterator();

        if (userList.size() == 0) {

            String json = gson.toJson(newUser);
            userListNew.add(json);

        }
        else {
            while (userListIterator.hasNext()) {
                User userAux = gson.fromJson(userListIterator.next(), User.class);
                if (newUser.getUsername().equals(userAux.getUsername())) {
                    String json = gson.toJson(newUser);
                    userListNew.add(json);
                } else {
                    String json = gson.toJson(userAux);
                    userListNew.add(json);
                }
            }
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(USERS_KEY_USERS, userListNew);
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

            setResult(RESULT_OK);
            finish();

        }

    }


    public User readUserFromPreferences() {
        User userAux = null;
        User user = null;
//        boolean encontrado = false;

        SharedPreferences prefsLogin = getSharedPreferences(USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        String userLogged = prefsLogin.getString(USER_LOGIN_PREFERENCES_KEY_USER, "");

        SharedPreferences prefs = getSharedPreferences(USERS, Context.MODE_PRIVATE);
        this.userList = prefs.getStringSet(USERS_KEY_USERS, null);

        Gson gson = new Gson();

        Iterator<String> userListIterator = this.userList.iterator();

        while (userListIterator.hasNext()) {
            userAux = gson.fromJson(userListIterator.next(), User.class);
            if (userLogged.equals(userAux.getUsername())) {
                user = userAux;
                break;
            }
        }

        return user;
    }
}
