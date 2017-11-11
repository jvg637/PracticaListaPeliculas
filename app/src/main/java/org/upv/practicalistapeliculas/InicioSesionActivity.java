package org.upv.practicalistapeliculas;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.upv.practicalistapeliculas.model.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class InicioSesionActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private  Set userList = new HashSet<User>();
    EditText contraseña;
    EditText usuario;
    CheckBox mostrar;
    CheckBox recordarme;

    private void initUserList() {
        this.prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);

        this.userList = prefs.getStringSet("users", userList );

        if( this.userList.size() == 0 ) {
            SharedPreferences.Editor editor = prefs.edit();

            User mainUser = new User("usuario1", "usuario1");


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
        this.userList = prefs.getStringSet("users", this.userList );
        Gson gson = new Gson();

        Iterator<String> userListIterator =  this.userList.iterator();
        Log.d("x", "Tamaño" + this.userList.size() + " User: " + userToCheck.getUsername());

        while( userListIterator.hasNext() && validUser == false) {
            userAux = gson.fromJson(userListIterator.next(), User.class);

            validUser = userAux.equals(userToCheck);
        }

        return validUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        contraseña = (EditText) findViewById(R.id.contraseña);
        usuario = (EditText) findViewById(R.id.usuario);
        mostrar = (CheckBox) findViewById(R.id.mostrar_contraseña);
        recordarme = (CheckBox) findViewById(R.id.recordarme);

        initUserList();
    }

    public void loguearCheckbox(View v) {
        String s = "Recordar datos de usuario: " + (recordarme.isChecked() ? "Sí" : "No");
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        if (recordarme.isChecked()){
            usuario.setText("usuario1");
            contraseña.setText("usuario1");
        }
    }

    public void mostrarContraseña(View v) {

        if (mostrar.isChecked()) {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
            contraseña.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void acceder (View view){
       /* if(usuario.getText().toString().equals("usuario1") && contraseña.getText().toString().equals("usuario1")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            String s = "Usuario o contraseña incorrecto, intentelo de nuevo";
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        }*/

        if( this.checkUser(new User(this.usuario.getText().toString(), this.contraseña.getText().toString())) ){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
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

    public void x (View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

}
