package com.example.borja.eurocity.model;

import lombok.Data;

/**
 * Created by borja on 6/01/17.
 */

@Data
public class Ejercicio {
    private String enunciado;
    private String categoria;
    private String solucion;
    private String lugar;

    public Ejercicio(String enunciado, String categoria, String solucion, String lugar){
        this.setSolucion(solucion);
        this.setEnunciado(enunciado);
        this.setLugar(lugar);
        this.setSolucion(solucion);
    }

}
