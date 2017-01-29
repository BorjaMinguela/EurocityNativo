package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.borja.eurocity.model.Fotos;
import com.example.borja.eurocity.model.ProgressTask;
import com.example.borja.eurocity.model.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MisDestinos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_destinos);
        requestFotos();
    }

    public List<String> getFotos(String lugar){
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String user = prefs.getString("name", "");
        List<String> urls = new ArrayList<>();
        RestClient rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestFotos?usuario=%s&ciudad=%s",user,lugar));
            JSONArray array = json.getJSONArray("foto");
            Fotos fotos = new Fotos();
            fotos.addFotosFromJson(array);
            urls=fotos.getFotosUrl();
        }
        catch (Exception e){
            Log.e("Error en mis destinos",e.toString());
        }
        return urls;
    }

    public void dibujar(String foto,String lugar){
        File fotoViaje = new File(foto);
        if(fotoViaje.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(fotoViaje.getAbsolutePath());
            ImageView imageView = new ImageView(this);
            Bitmap scaled=Bitmap.createScaledBitmap(bitmap,500,300,false);//Para API level 15
            imageView.setImageBitmap(scaled);
            LinearLayout group=null;
            switch (lugar){
                case "Lisboa":
                    group = (LinearLayout) findViewById(R.id.destinos_lisboa);
                    break;
                case "Londres":
                    group = (LinearLayout) findViewById(R.id.destinos_londres);
                    break;
                case "Paris":
                    group = (LinearLayout) findViewById(R.id.destinos_paris);
                    break;
                case "Roma":
                    group = (LinearLayout) findViewById(R.id.destinos_roma);
                    break;
            }
            group.addView(imageView);
        }
    }

    public void requestFotos(){
        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<List<List<String>>>(this) {
                    @Override
                    protected List<List<String>> work() throws Exception {
                        List<List<String>> fotos = new ArrayList<>();
                        List<String> fotosLugar;
                        fotosLugar=getFotos("Lisboa");
                        fotos.add(fotosLugar);
                        fotosLugar=getFotos("Londres");
                        fotos.add(fotosLugar);
                        fotosLugar=getFotos("Paris");
                        fotos.add(fotosLugar);
                        fotosLugar=getFotos("Roma");
                        fotos.add(fotosLugar);
                        return fotos;
                    }

                    @Override
                    protected void onFinish(List<List<String>> response) {
                        for(String image:response.get(0)){
                            dibujar(image,"Lisboa");
                        }
                        for(String image:response.get(1)){
                            dibujar(image,"Londres");
                        }
                        for(String image:response.get(2)){
                            dibujar(image,"Paris");
                        }
                        for(String image:response.get(3)){
                            dibujar(image,"Roma");
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
}
