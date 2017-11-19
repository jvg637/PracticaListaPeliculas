package org.upv.practicalistapeliculas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.upv.practicalistapeliculas.R;
import org.upv.practicalistapeliculas.model.User;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Ivan on 10/11/17.
 */

public class RegistroActivity extends AppCompatActivity {

    private EditText usuario ;
    private EditText contraseña ;
    private EditText email ;
    private Set userList = new HashSet<User>();
    private Button bRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_alta);

        usuario = (EditText) findViewById(R.id.nombreUsuario_formularioAlta);
        contraseña = (EditText) findViewById(R.id.contraseña_formularioAlta);
        email = (EditText) findViewById(R.id.email_formularioAlta);
        bRegistrar = (Button) findViewById(R.id.boton_registrar);


        if (getIntent().getExtras()!=null) {
            Bundle extras = getIntent().getExtras();
            String txtUsuario= extras.getString("usuario", "");
            String txtPassword = extras.getString("password", "");
            usuario.setText(txtUsuario);
            contraseña.setText(txtPassword);
        }
        bRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrar();
            }
        });
    }

    public void registrar(){
        SharedPreferences prefs = getSharedPreferences("Usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        this.userList = prefs.getStringSet("users", userList );

        User mainUser = new User(usuario.getText().toString(), contraseña.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(mainUser);

        userList.add(json);

        editor.putStringSet("users", userList);
        editor.commit();

        Intent data = new Intent();
        data.putExtra("usuario", usuario.getText().toString());
        data.putExtra("password", contraseña.getText().toString());
        setResult(Activity.RESULT_OK,data);

        finish();
    }

    public void onStop() {
        super.onStop();
        finish();
    }


}
