package org.upv.movie.list.netflix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
    String[] textArray = {"", "", "", "", "", ""};
    Integer[] imageArray = {R.drawable.ic_fav, R.drawable.ic_star, R.drawable.ic_thumb_up, R.drawable.ic_thumb_down, R.drawable.ic_schedule, R.drawable.ic_help};
    Button ok;
    Button cancel;
    String option = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_lista);

        titulo = findViewById(R.id.et_titulo_anl);
        descripcion = findViewById(R.id.et_descripcion_anl);

        icono = findViewById(R.id.sp_icono_anl);
        SpinnerAdapter adapter = new org.upv.movie.list.netflix.adapters.SpinnerAdapter(this, R.layout.spinner_item_layout, textArray, imageArray);
        icono.setAdapter(adapter);


        Intent data = getIntent();
        if (data != null) {
            // Edit mode
            Bundle extras = data.getExtras();
            if (extras != null) {
                titulo.setText(extras.getString("title"));
                descripcion.setText(extras.getString("description"));
                int iconImg = extras.getInt("icon");
                int pos = 0;
                for (int posAux : imageArray) {
                    if (iconImg == posAux) {
                        break;
                    }
                    pos++;
                }
                icono.setSelection(pos);
                option = "edit";
            } else {
                option = "new";
            }
        } else {
            option = "new";
        }


        ok = findViewById(R.id.bt_ok_anl);
        ok.setText(android.R.string.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!titulo.getText().toString().isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("titulo", titulo.getText().toString());
                    intent.putExtra("descripcion", descripcion.getText().toString());
                    intent.putExtra("icono", icono.getSelectedItemPosition());
                    intent.putExtra("option", option);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Snackbar.make(findViewById(R.id.contenedor), R.string.field_empty, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        cancel = findViewById(R.id.bt_cancel_anl);
        cancel.setText(android.R.string.cancel);
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
