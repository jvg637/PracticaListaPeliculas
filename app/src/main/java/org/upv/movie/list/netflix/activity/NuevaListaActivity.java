package org.upv.movie.list.netflix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.upv.movie.list.netflix.R;

/**
 * Created by Lionel on 10/12/2017.
 */

public class NuevaListaActivity extends AppCompatActivity {

    EditText titulo;
    EditText descripcion;
    Spinner icono;
    //String [] textArray = {"image 1", "image 2"};
    String[] textArray = {"", "", "", ""};
    Integer[] imageArray = {R.drawable.ic_fav, R.drawable.ic_star, R.drawable.ic_thumb_up, R.drawable.ic_thumb_down};
    Button ok;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_lista);

        titulo = (EditText)findViewById(R.id.et_titulo_anl);
        descripcion = (EditText)findViewById(R.id.et_descripcion_anl);

        icono = (Spinner) findViewById(R.id.sp_icono_anl);
        SpinnerAdapter adapter = new org.upv.movie.list.netflix.adapters.SpinnerAdapter(this, R.layout.spinner_item_layout, textArray, imageArray);
        icono.setAdapter(adapter);

        ok = (Button) findViewById(R.id.bt_ok_anl);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("titulo", titulo.getText().toString());
                intent.putExtra("descripcion", descripcion.getText().toString());
                intent.putExtra("icono", icono.getSelectedItemPosition());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        cancel = (Button) findViewById(R.id.bt_cancel_anl);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
