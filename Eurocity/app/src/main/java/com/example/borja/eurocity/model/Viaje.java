package com.example.borja.eurocity.model;

import android.graphics.Color;

import com.example.borja.eurocity.R;

import java.io.Serializable;

/**
 * Created by borja on 4/01/17.
 */

public class Viaje  implements Serializable {
    private String nombre;

    public Viaje(String nombre){
        this.setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getColor() {
        switch (nombre){
            case "lisboa":
                return R.color.LimeGreen;
            case "londres":
                return R.color.Orange;
            case "paris":
                return R.color.Yellow;
            case "roma":
                return R.color.MediumTurquoise;

        }
        return R.color.Maroon;
    }
}
