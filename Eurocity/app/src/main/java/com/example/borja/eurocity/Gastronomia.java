package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borja.eurocity.model.Fotos;
import com.example.borja.eurocity.model.ProgressTask;
import com.example.borja.eurocity.model.RestClient;
import com.example.borja.eurocity.model.Viaje;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Gastronomia extends AppCompatActivity {
    Viaje viaje;
    RestClient rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastronomia);
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String lugar= prefs.getString("lugar", "");

        LinearLayout group = (LinearLayout) findViewById(R.id.gastronomia_text);
        viaje=new Viaje(lugar);
        for(String texto : viaje.getGastronomia()){
            TextView textView = new TextView(this);
            textView.setText(texto);
            textView.setTypeface(null, Typeface.BOLD);
            group.addView(textView);
        }

        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<List<Bitmap>>(this) {
                    @Override
                    protected List<Bitmap> work() throws Exception {
                        List<String> urls=getFotos();
                        List<Bitmap> bitmaps=new ArrayList();
                        for(String url:urls){
                            bitmaps.add(getImage(url));
                        }
                        return bitmaps;
                    }

                    @Override
                    protected void onFinish(List<Bitmap> response) {
                        for(Bitmap image:response){
                            dibujar(image);
                        }
                    }
                }.execute();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, R.string.no_internet,Toast.LENGTH_SHORT).show();
        }


    }

    public Bitmap getImage(String url){
        return RestClient.loadImageFromUrl(url);
    }

    public void dibujar(Bitmap foto){
        LinearLayout group = (LinearLayout) findViewById(R.id.gastronomia_text);
        Bitmap scaled;
        scaled=Bitmap.createScaledBitmap(foto,500,300,false);//Para API level 15
        ImageView imageView=new ImageView(this);
        imageView.setImageBitmap(scaled);
        group.addView(imageView);
    }


    public List<String> getFotos(){
        List<String> urls = new ArrayList<>();
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestFotos?usuario=gastronomia&ciudad=%s", viaje.getNombre()));
            JSONArray array = json.getJSONArray("foto");
            Fotos fotos = new Fotos();
            fotos.addFotosFromJson(array);
            urls=fotos.getFotosUrl();
        }
        catch (Exception e){
            Log.e("Error en gastronomia",e.toString());
        }
        return urls;
    }
}
