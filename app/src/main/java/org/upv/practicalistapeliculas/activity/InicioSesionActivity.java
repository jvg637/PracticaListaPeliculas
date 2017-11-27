package org.upv.practicalistapeliculas.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.upv.practicalistapeliculas.R;
import org.upv.practicalistapeliculas.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InicioSesionActivity extends AppCompatActivity {

    private static final int ALTA_USUARIO = 20001;
    private SharedPreferences prefs;
    private Set userList = new HashSet<User>();
    private boolean recordarUsuario = false;
    EditText contraseña;
    EditText usuario;
    CheckBox mostrar;
    CheckBox recordarme;

    private void initUserList() {
        this.prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);

        this.userList = prefs.getStringSet("users", userList);

        if (this.userList.size() == 0) {
            SharedPreferences.Editor editor = prefs.edit();

            User mainUser = new User("usuario1", "usuario1");
            mainUser.setDEFAULT_PHOTO(R.mipmap.ic_perfil_round);
            Gson gson = new Gson();
            String json = gson.toJson(mainUser);

            userList.add(json);
            editor.putStringSet("users", userList);
            editor.commit();
        }
    }

    private boolean checkUser(User userToCheck) {
        User userAux = null;
        boolean validUser = false;
        this.userList = prefs.getStringSet("users", this.userList);
        Gson gson = new Gson();

        Iterator<String> userListIterator = this.userList.iterator();
        Log.d("x", "Tamaño" + this.userList.size() + " User: " + userToCheck.getUsername());

        while (userListIterator.hasNext() && validUser == false) {
            userAux = gson.fromJson(userListIterator.next(), User.class);

            validUser = userAux.equals(userToCheck);
        }

        return validUser;
    }

    private void rememberUser() {
        SharedPreferences prefs = getSharedPreferences("Recordar usuario", Context.MODE_PRIVATE);
        String username = "";
        String password = "";

        Log.e("x", "recordando");

        if (prefs.getBoolean("recordar", this.recordarUsuario)) {
            this.recordarme.setChecked(true);
            this.usuario.setText(prefs.getString("username", username));
            this.contraseña.setText(prefs.getString("password", password));
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
        String s = "Recordar datos de usuario: " + (recordarme.isChecked() ? "Sí" : "No");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

        this.recordarUsuario = recordarme.isChecked();

    }

    public void mostrarContraseña(View v) {

        if (mostrar.isChecked()) {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void acceder(View view) {
       /* if(usuario.getText().toString().equals("usuario1") && contraseña.getText().toString().equals("usuario1")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            String s = "Usuario o contraseña incorrecto, intentelo de nuevo";
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }*/

        if (this.checkUser(new User(this.usuario.getText().toString(), this.contraseña.getText().toString()))) {
            SharedPreferences prefs = getSharedPreferences("Recordar usuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("recordar", recordarme.isChecked());

            if (recordarUsuario) {
                editor.putString("username", this.usuario.getText().toString());
                editor.putString("password", this.contraseña.getText().toString());
            }

            editor.commit();

            // Graba en preferencias el login
            SharedPreferences prefsLogin = getSharedPreferences(PerfilActivity.USER_LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorLogin = prefsLogin.edit();
            editorLogin.putString(PerfilActivity.USER_LOGIN_PREFERENCES_KEY_USER, this.usuario.getText().toString());
            editorLogin.commit();

            Intent intent = new Intent(this, ListasActivity.class);
//            startActivity(intent);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            String s = "Usuario o contraseña incorrecto, intentelo de nuevo";
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }
    }

    /*public void borrarCampos (View view){
        //EditText contraseña = (EditText) findViewById(R.id.contraseña);
        usuario.setText("");
        contraseña.setText("");
        usuario.requestFocus();
    }*/

    public void altaUsuario(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
//        startActivity(intent);
        intent.putExtra("usuario", usuario.getText().toString());
        intent.putExtra("password", contraseña.getText().toString());
        ActivityCompat.startActivityForResult(this, intent, ALTA_USUARIO, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ALTA_USUARIO && resultCode == Activity.RESULT_OK) {
            usuario.setText(data.getExtras().getString("usuario"));
            contraseña.setText(data.getExtras().getString("password"));
        }
    }
}
