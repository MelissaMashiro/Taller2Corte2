package com.example.visitantes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Visitante> visitantes;

    int id;
    int indice;

    ControladorDB db;

    int tipoVisitante;

    EditText et_cedula, et_nombre, et_apto;
    Spinner sp_tipo;
    Button guardar, lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = findViewById(R.id.txt_nombre);
        et_cedula = findViewById(R.id.txt_cedula);
        et_apto = findViewById(R.id.txt_apto);
        sp_tipo = findViewById(R.id.spinner);

        guardar = findViewById(R.id.guardar);
        lista = findViewById(R.id.lista);

        Intent intent = getIntent();
        indice = intent.getIntExtra("indice", 0);

        db = new ControladorDB(getApplicationContext());

        visitantes = db.optenerRegistros();

        Visitante visitante = visitantes.get(indice);
        id = visitante.getCedula();

        et_cedula.setText(Integer.toString(visitante.getCedula()));
        et_apto.setText(visitante.getApto());
        et_nombre.setText(visitante.getNombre());


        ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
                , R.array.tipos_visitantes, R.layout.support_simple_spinner_dropdown_item);
        sp_tipo.setAdapter(otroAdacter);

        sp_tipo.setSelection(visitante.getTipoVisitante());

        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoVisitante = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lista.setText("cancelar");
        guardar.setOnClickListener(this);
        lista.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guardar:
                try {
                    if (!et_cedula.getText().toString().isEmpty() && !et_cedula.getText().toString().isEmpty() &&
                            !et_cedula.getText().toString().isEmpty()) {
                        int cedula = Integer.parseInt(et_cedula.getText().toString());
                        Visitante visitante =
                                new Visitante(cedula, et_nombre.getText().toString(), visitantes.get(indice).getFecha(), et_apto.getText().toString(), tipoVisitante);
                        int retorno = db.modificar(visitante);
                        if (retorno == 0) {
                            Toast.makeText(getApplicationContext(), "a ocurrido un error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "modificacion exitoso", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lista:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }
    }
}
