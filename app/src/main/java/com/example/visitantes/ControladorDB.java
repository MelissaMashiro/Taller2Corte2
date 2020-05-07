package com.example.visitantes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ControladorDB {
    private AdminSQLiteOpenHelper baseDatos;

    public ControladorDB(Context context) {
        this.baseDatos = new AdminSQLiteOpenHelper(context, ModeloDB.NOMBRE_DB, null, 1);
    }

    public long Registrar(Visitante visitante) {
        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(ModeloDB.COL_CEDULA, visitante.getCedula());
        registro.put(ModeloDB.COL_NOMBRE, visitante.getNombre());
        registro.put(ModeloDB.COL_FECHA, visitante.getFecha().getTimeInMillis());
        registro.put(ModeloDB.COL_APTO, visitante.getApto());
        registro.put(ModeloDB.COL_TIPO_VISITANTE, visitante.getTipoVisitante());
        return BaseDeDatos.insert(ModeloDB.NOMBRE_TABLA, null, registro);
    }

    public int eliminar(Visitante visitante) {
        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(visitante.getCedula())};
        return BaseDeDatos.delete(ModeloDB.NOMBRE_TABLA, ModeloDB.COL_CEDULA + " = ?", argumentos);
    }

    public int modificar(Visitante visitante) {
        SQLiteDatabase BaseDeDatos = baseDatos.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(ModeloDB.COL_NOMBRE, visitante.getNombre());
        registro.put(ModeloDB.COL_FECHA, visitante.getFecha().getTimeInMillis());
        registro.put(ModeloDB.COL_APTO, visitante.getApto());
        registro.put(ModeloDB.COL_TIPO_VISITANTE, visitante.getTipoVisitante());

        String campoParaActualizar = ModeloDB.COL_CEDULA + " = ?";
        String[] argumentosParaActualizar = {String.valueOf(visitante.getCedula())};

        return BaseDeDatos.update(ModeloDB.NOMBRE_TABLA, registro, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Visitante> optenerRegistros() {
        ArrayList<Visitante> visitantes = new ArrayList<>();

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[] columnasConsultar = {ModeloDB.COL_CEDULA, ModeloDB.COL_NOMBRE, ModeloDB.COL_FECHA
                , ModeloDB.COL_APTO, ModeloDB.COL_TIPO_VISITANTE};

        Cursor cursor = database.query(ModeloDB.NOMBRE_TABLA, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return visitantes;
        }

        if (!cursor.moveToFirst()) {
            return visitantes;
        }

        do {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(cursor.getLong(2));
            Visitante visitante = new Visitante(cursor.getInt(0), cursor.getString(1)
                    , calendar, cursor.getString(3), cursor.getInt(4));
            visitantes.add(visitante);
        } while (cursor.moveToNext());

        cursor.close();
        return visitantes;
    }
}
