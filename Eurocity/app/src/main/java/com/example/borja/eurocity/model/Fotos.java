package com.example.borja.eurocity.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by borja on 18/01/17.
 */

@Data
public class Fotos {
    private List<Foto> fotos = new ArrayList<>();

    public void addFotosFromJson(JSONArray array){
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                Foto foto= new Foto();
                foto.setLugar(item.getString("lugar"));
                foto.setUser(item.getString("user"));
                foto.setPath(item.getString("path"));
                fotos.add(foto);
            }
        }
        catch (Exception e){

        }
    }

    public List<String> getFotosUrl(){
        List<String> urls = new ArrayList<>();
        for(Foto foto:this.getFotos()){
            String url=foto.getPath();
            urls.add(url);
        }
        return urls;
    }
}
