package com.example.visitantes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {
    ListView Lista;
    ArrayList<Visitante> ListaArray;
    ArrayList<String> Listastring;

    ControladorDB controladorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Lista = findViewById(R.id.Lista);
        controladorDB = new ControladorDB(getApplicationContext());
        ListaArray = controladorDB.optenerRegistros();
        Listastring = mostrarLista(ListaArray);

        ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Listastring);
        Lista.setAdapter(ArrayAdp);
        registerForContextMenu(Lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Visitante> listatemp = controladorDB.optenerRegistros();
                Listastring = mostrarLista(listatemp);
                ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Listastring);
                Lista.setAdapter(ArrayAdp);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.Item1:
                modificarVisitante(menuInfo.position);
                return true;
            case R.id.Item2:
                eliminarVisitante(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void eliminarVisitante(int position) {
        int retorno = controladorDB.eliminar(ListaArray.get(position));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "registro eliminado", Toast.LENGTH_SHORT).show();
            ListaArray = controladorDB.optenerRegistros();
            Listastring = mostrarLista(ListaArray);
            ArrayAdapter ArrayAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Listastring);
            Lista.setAdapter(ArrayAdp);
        } else {
            Toast.makeText(getApplicationContext(), "error al borrar", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarVisitante(int position) {
        Intent i = new Intent(this, ModificarActivity.class);
        i.putExtra("indice", position);
        startActivityForResult(i, 2);
    }

    private ArrayList<String> mostrarLista(ArrayList<Visitante> visitantes) {
        ArrayList<String> temp = new ArrayList<>();
        for (Visitante visitante : visitantes) {
            String tempTipo = "";
            switch (visitante.getTipoVisitante()) {
                case 0:
                    tempTipo = "familia";
                    break;
                case 1:
                    tempTipo = "amigo";
                    break;
                case 2:
                    tempTipo = "domiciliario";
                    break;
            }
            String temp2 = visitante.getCedula() + " " + visitante.getNombre() + " " + visitante.stingFecha() + " \n " +
                    visitante.getApto() + " " + tempTipo;
            temp.add(temp2);
        }
        return temp;
    }
}
