package com.example.borja.eurocity.model;

import android.util.Log;

import org.json.JSONObject;

import lombok.Data;

/**
 * Created by borja on 6/01/17.
 */

@Data
public class Comentario {
    private String comentario;
    private String categoria;
    private String user;
    private String lugar;

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("comentario", comentario);
            json.put("categoria",categoria);
            json.put("user", user);
            json.put("lugar", lugar);
        }
        catch (Exception e){
            Log.e("Error comentario",e.toString());
        }
        return json;
    }
}
