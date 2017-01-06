package com.example.borja.eurocity.model;

/**
 * Created by borja on 6/01/17.
 */

public class ValoracionModel {
    private String nombre;
    private String lugar;
    private int nota;

    public ValoracionModel(String nombre, String lugar, int nota){
        this.setLugar(lugar);
        this.setNombre(nombre);
        this.setNota(nota);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
