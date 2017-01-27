package com.example.borja.eurocity.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by borja on 27/01/17.
 */

@Data
public class Comentarios {

    private List<Comentario> comentarios = new ArrayList<>();

    public void addComentariosFromJson(JSONArray array){
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                Comentario comentario= new Comentario();
                comentario.setUser(item.getString("user"));
                comentario.setComentario(item.getString("comentario"));
                comentario.setCategoria(item.getString("categoria"));
                comentario.setLugar(item.getString("lugar"));
                comentarios.add(comentario);
            }
        }
        catch (Exception e){

        }
    }

    public List<String> getForo(){
        List<String> comments = new ArrayList<>();
        for(Comentario comentario:this.getComentarios()){
            String comment=comentario.getComentario();
            comments.add(comment);
        }
        return comments;
    }
}
