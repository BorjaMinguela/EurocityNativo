package com.example.borja.eurocity.model;

import android.util.Log;

import org.json.JSONObject;

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

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("nota", nota);
            json.put("user", nombre);
            json.put("lugar", lugar);
        }
        catch (Exception e){
            Log.e("Error valoracion",e.toString());
        }
        return json;
    }

}
