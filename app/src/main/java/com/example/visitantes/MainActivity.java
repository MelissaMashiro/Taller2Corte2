package com.example.visitantes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ControladorDB db;

    int tipoVisitante;

    EditText et_cedula, et_nombre, et_apto;
    Spinner sp_tipo;
    Button guardar, lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = findViewById(R.id.txt_nombre);
        et_cedula = findViewById(R.id.txt_cedula);
        et_apto = findViewById(R.id.txt_apto);
        sp_tipo = findViewById(R.id.spinner);

        guardar = findViewById(R.id.guardar);
        lista = findViewById(R.id.lista);

        db = new ControladorDB(getApplicationContext());

        ArrayAdapter<CharSequence> otroAdacter = ArrayAdapter.createFromResource(this
                , R.array.tipos_visitantes, R.layout.support_simple_spinner_dropdown_item);
        sp_tipo.setAdapter(otroAdacter);

        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoVisitante = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        guardar.setOnClickListener(this);
        lista.setOnClickListener(this);
    }

    public void listar() {
        Intent i = new Intent(this, ListaActivity.class);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.guardar:
                try {
                    if (!et_cedula.getText().toString().isEmpty() && !et_cedula.getText().toString().isEmpty() &&
                            !et_cedula.getText().toString().isEmpty()) {
                        Calendar calendar = Calendar.getInstance();
                        int cedula = Integer.parseInt(et_cedula.getText().toString());
                        Visitante visitante =
                                new Visitante(cedula, et_nombre.getText().toString(), calendar, et_apto.getText().toString(), tipoVisitante);
                        long retorno = db.Registrar(visitante);
                        limpiarCampo();
                        if (retorno==-1){
                            Toast.makeText(getApplicationContext(),"a ocurrido un error",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"registro exitoso",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lista:
                listar();
                break;
        }
    }

    private void limpiarCampo() {
        et_nombre.setText("");
        et_cedula.setText("");
        et_apto.setText("");
    }
}
