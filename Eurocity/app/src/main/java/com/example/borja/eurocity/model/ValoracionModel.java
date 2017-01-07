package com.example.borja.eurocity.model;

import lombok.Data;

/**
 * Created by borja on 6/01/17.
 */

@Data
public class ValoracionModel {
    private String nombre;
    private String lugar;
    private int nota;

    public ValoracionModel(String nombre, String lugar, int nota){
        this.setLugar(lugar);
        this.setNombre(nombre);
        this.setNota(nota);
    }

}
