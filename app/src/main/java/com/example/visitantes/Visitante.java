package com.example.visitantes;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Visitante {
    private int cedula;
    private String nombre;
    private String apto;
    private int tipoVisitante;
    private Calendar fecha;

    public Visitante(int cedula, String nombre, Calendar fecha, String apto, int tipoVisitante) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apto = apto;
        this.tipoVisitante = tipoVisitante;
        this.fecha = fecha;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApto() {
        return apto;
    }

    public void setApto(String apto) {
        this.apto = apto;
    }

    public int getTipoVisitante() {
        return tipoVisitante;
    }

    public void setTipoVisitante(int tipoVisitante) {
        this.tipoVisitante = tipoVisitante;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public String stingFecha() {
        return this.fecha.get(Calendar.DAY_OF_MONTH) + "-" + (this.fecha.get(Calendar.MONTH) + 1) + "-" +
                this.fecha.get(Calendar.YEAR) + " " + this.fecha.get(Calendar.HOUR_OF_DAY) + ":" +
                this.fecha.get(Calendar.MINUTE);
    }
}
