package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.borja.eurocity.model.Comentario;
import com.example.borja.eurocity.model.Comentarios;
import com.example.borja.eurocity.model.Fotos;
import com.example.borja.eurocity.model.ProgressTask;
import com.example.borja.eurocity.model.RestClient;
import com.example.borja.eurocity.model.Viaje;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LugaresEmblematicos extends AppCompatActivity {
    private Viaje viaje;
    RestClient rest;
    private Comentario com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_emblematicos);
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String lugar= prefs.getString("lugar", "");
        LinearLayout group = (LinearLayout) findViewById(R.id.emblematicos_text);
        viaje=new Viaje(lugar);
        for(String texto : viaje.getLugaresEmblematicos()){
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
        if(foto==null) return;
        LinearLayout group = (LinearLayout) findViewById(R.id.emblematicos_text);
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

    public void loadForo(View view){
        EditText editText = (EditText) findViewById(R.id.emblematicos_comentario);
        editText.setVisibility(View.VISIBLE);
        Button button = (Button) findViewById(R.id.emblematicos_btn_comentario);
        button.setVisibility(View.VISIBLE);
        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<Comentarios>(this) {
                    @Override
                    protected Comentarios work() throws Exception {
                        Comentarios comentarios=getComentarios();
                        return comentarios;
                    }

                    @Override
                    protected void onFinish(Comentarios response) {
                        cargarForo(response);
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

    public Comentarios getComentarios(){
        Comentarios comentarios = new Comentarios();
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestComentarios?categoria=interes&ciudad=%s", viaje.getNombre()));
            JSONArray array = json.getJSONArray("comentario");
            comentarios.addComentariosFromJson(array);
            //comentarios=comments.getForo();

        }
        catch (Exception e){
            Log.e("Error en gastronomia",e.toString());
        }
        return comentarios;
    }

    public void cargarForo(Comentarios comentarios){
        LinearLayout group = (LinearLayout) findViewById(R.id.emblematicos_foro);
        group.removeAllViews();
        for(Comentario comentario:comentarios.getComentarios()){
            String texto="<b>"+comentario.getUser()+"</b>: "+comentario.getComentario();
            Spanned text = Html.fromHtml(texto);
            TextView textView = new TextView(this);
            textView.setText(text);
            group.addView(textView);
        }
    }

    public void sendComentario(View view){
        final String comentario=((EditText)findViewById(R.id.emblematicos_comentario)).getText().toString().trim();
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String user= prefs.getString("name", "");
        final String lugar= prefs.getString("lugar", "");
        com=new Comentario();
        com.setLugar(lugar);
        com.setUser(user);
        com.setCategoria("interes");
        com.setComentario(comentario);
        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<Comentarios>(this) {
                    @Override
                    protected Comentarios work() throws Exception {
                        int response=uploadComentario();
                        Comentarios comentarios=getComentarios();
                        return comentarios;
                    }

                    @Override
                    protected void onFinish(Comentarios response) {
                        cargarForo(response);
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

    public int uploadComentario(){
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = com.toJson();
            int result=rest.postJson(json,"addComentario");
            Log.i("Valoracion response",Integer.toString(result));
            return result;

        }
        catch (Exception e){
            return -1;
        }
    }
}
