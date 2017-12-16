package org.upv.movie.list.netflix.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.upv.movie.list.netflix.R;
import org.upv.movie.list.netflix.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InicioSesionActivity extends AppCompatActivity {

    private static final int ALTA_USUARIO = 20001;
    private SharedPreferences prefs;
    private Set userList;
    private boolean recordarUsuario = false;
    EditText contraseña;
    EditText usuario;
    CheckBox mostrar;
    CheckBox recordarme;

    private void initUserList() {
        prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);

        userList = prefs.getStringSet("users", userList);

        if (userList == null || userList.size() == 0) {
            userList = new HashSet<User>();
            SharedPreferences.Editor editor = prefs.edit();

            User mainUser = new User("usuario1", "usuario1");
            mainUser.setDEFAULT_PHOTO(R.mipmap.ic_perfil_round);
            Gson gson = new Gson();
            String json = gson.toJson(mainUser);

            userList.add(json);
            editor.putStringSet("users", userList);
            editor.apply();
        }
    }

    private boolean checkUser(User userToCheck) {
        User userAux;
        boolean validUser = false;
        userList = prefs.getStringSet("users", userList);
        Gson gson = new Gson();
        Iterator userListIterator = userList.iterator();

        while (userListIterator.hasNext() && !validUser) {
            userAux = gson.fromJson((String) userListIterator.next(), User.class);
            validUser = userAux.equals(userToCheck);
        }
        return validUser;
    }

    private void rememberUser() {
        SharedPreferences prefs = getSharedPreferences("Recordar usuario", Context.MODE_PRIVATE);
        String username = "";
        String password = "";

        if (prefs.getBoolean("recordar", recordarUsuario)) {
            recordarme.setChecked(true);
            usuario.setText(prefs.getString("username", username));
            contraseña.setText(prefs.getString("password", password));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        contraseña = findViewById(R.id.contraseña);
        usuario = findViewById(R.id.usuario);
        mostrar = findViewById(R.id.mostrar_contraseña);
        recordarme = findViewById(R.id.recordarme);

        rememberUser();
        initUserList();
    }

    public void loguearCheckbox(View v) {
        String s = getString(R.string.ISA_remember) + (recordarme.isChecked() ? getString(android.R.string.yes) : getString(android.R.string.no));
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        recordarUsuario = recordarme.isChecked();
    }

    public void mostrarContrasena(View v) {
        if (mostrar.isChecked()) {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void acceder(View view) {
        if (checkUser(new User(usuario.getText().toString(), contraseña.getText().toString()))) {
            SharedPreferences prefs = getSharedPreferences("Recordar usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("recordar", recordarme.isChecked());

            if (recordarUsuario) {
                editor.putString("username", usuario.getText().toString());
                editor.putString("password", contraseña.getText().toString());
            }
            editor.apply();

            // Graba en preferencias el login
            SharedPreferences prefsLogin = getSharedPreferences(PerfilActivity.USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorLogin = prefsLogin.edit();
            editorLogin.putString(PerfilActivity.USER_LOGIN_PREFERENCES_KEY_USER, usuario.getText().toString());
            editorLogin.apply();

            Intent intent = new Intent(this, ListasActivity.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                startActivity(intent);
            }
        } else {
            String s = getString(R.string.ISA_bad_user);
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    public void altaUsuario(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        intent.putExtra("usuario", usuario.getText().toString());
        intent.putExtra("password", contraseña.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.startActivityForResult(this, intent, ALTA_USUARIO, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALTA_USUARIO && resultCode == Activity.RESULT_OK) {
            usuario.setText(data.getExtras().getString("usuario"));
            contraseña.setText(data.getExtras().getString("password"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
