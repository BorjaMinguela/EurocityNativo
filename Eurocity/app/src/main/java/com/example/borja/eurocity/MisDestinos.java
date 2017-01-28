package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.borja.eurocity.model.Fotos;
import com.example.borja.eurocity.model.RestClient;
import com.example.borja.eurocity.model.Viaje;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MisDestinos extends AppCompatActivity {
    private RestClient rest;
    private Viaje viaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_destinos);
    }

    public List<String> getFotos(){
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String lugar= prefs.getString("lugar", "");
        final String user = prefs.getString("name", "");
        viaje=new Viaje(lugar);
        List<String> urls = new ArrayList<>();
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestFotos?usuario=interes&ciudad=%s", viaje.getNombre()));
            JSONArray array = json.getJSONArray("foto");
            Fotos fotos = new Fotos();
            fotos.addFotosFromJson(array);
            urls=fotos.getFotosUrl();
        }
        catch (Exception e){
            Log.e("Error en lugares_emb",e.toString());
        }
        return urls;
    }
}
