package com.example.borja.eurocity.model;

/**
 * Created by borja on 6/01/17.
 */

public class Nota {
    private String user;
    private int nota;
    private Ejercicio ejercicio;

    public Nota(String user, int nota, Ejercicio ejercicio){
        this.setEjercicio(ejercicio);
        this.setNota(nota);
        this.setUser(user);
    }
    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
