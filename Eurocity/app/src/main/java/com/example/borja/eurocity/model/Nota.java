package com.example.borja.eurocity.model;

import lombok.Data;

/**
 * Created by borja on 6/01/17.
 */

@Data
public class Nota {
    private String user;
    private int nota;
    private Ejercicio ejercicio;

    public Nota(String user, int nota, Ejercicio ejercicio){
        this.setEjercicio(ejercicio);
        this.setNota(nota);
        this.setUser(user);
    }
}
