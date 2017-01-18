package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borja.eurocity.model.Foto;
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
        /*if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<Drawable>(this) {
                    @Override
                    protected Drawable work() throws Exception {
                        Drawable response=getImage("http://www.manyar.com.mx/web/images/manyar-comida-saludable-inicio-1.jpg");
                        return response;
                    }

                    @Override
                    protected void onFinish(Drawable result) {

                        dibujar(result);
                    }
                }.execute();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, R.string.no_internet,Toast.LENGTH_SHORT).show();
        }*/

        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<List<Drawable>>(this) {
                    @Override
                    protected List<Drawable> work() throws Exception {
                        List<String> urls=getFotos();
                        List<Drawable> drawables=new ArrayList();
                        for(String url:urls){
                            drawables.add(getImage(url));
                        }
                        return drawables;
                    }

                    @Override
                    protected void onFinish(List<Drawable> response) {
                        for(Drawable image:response){
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

    public Drawable getImage(String url){
        return RestClient.loadImageFromWebOperations(url);
    }

    public void dibujar(Drawable foto){
        LinearLayout group = (LinearLayout) findViewById(R.id.gastronomia_text);//Cambiar

        ImageView imageView=new ImageView(this);
        imageView.setImageDrawable(foto);
        //android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        //layoutParams.width = 80;
        //layoutParams.height = 80;
        //imageView.setLayoutParams(layoutParams);
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
