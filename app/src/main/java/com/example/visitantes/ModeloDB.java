package com.example.visitantes;

public class ModeloDB {
    public static final String NOMBRE_DB = "administracion";
    public static final String NOMBRE_TABLA = "visita";
    public static final String COL_CEDULA = "cedula";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_FECHA = "fecha";
    public static final String COL_APTO = "apto";
    public static final String COL_TIPO_VISITANTE = "tipo";

    public static final String CREAR_TABLA_VISITANTE = "CREATE TABLE " +
            "" + NOMBRE_TABLA + " ( " + COL_CEDULA + " INTEGER PRIMARY KEY, " +
            " " + COL_NOMBRE + " TEXT, " + COL_FECHA + " INTEGER, " +
            " " + COL_APTO + " TEXT, " + COL_TIPO_VISITANTE + " INTEGER)";
}
