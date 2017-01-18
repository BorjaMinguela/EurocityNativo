package com.example.borja.eurocity.model;

import android.util.Log;

import org.json.JSONObject;

import lombok.Data;

/**
 * Created by borja on 18/01/17.
 */

@Data
public class Foto {
    String path;
    String user;
    String lugar;


    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("path", path);
            json.put("user", user);
            json.put("lugar", lugar);
        }
        catch (Exception e){
            Log.e("Error valoracion",e.toString());
        }
        return json;
    }
}
