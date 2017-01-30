package com.example.borja.eurocity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.borja.eurocity.model.Foto;
import com.example.borja.eurocity.model.RestClient;
import com.example.borja.eurocity.model.Viaje;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class ViajePpal extends AppCompatActivity {
    private Viaje viaje;
    private TextToSpeech tts;
    private RestClient rest;
    Uri mediaUri;
    final int PICTURE_REQUEST_CODE=1;
    final File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaje_ppal);
        //viaje= (Viaje) getIntent().getSerializableExtra("Viaje");
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        String lugar= prefs.getString("lugar", "");
        viaje=new Viaje(lugar);
        ((Button) findViewById(R.id.btn1)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn2)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn3)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn4)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn5)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        WebView wView=(WebView)findViewById(R.id.vppal_web);
        wView.loadUrl("file:///android_asset/doge.html");
        wView.setBackgroundColor(Color.TRANSPARENT);
        wView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        wView.setInitialScale(1);
        wView.setVerticalScrollBarEnabled(false);

        tts=new TextToSpeech(this,new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(new Locale("spa", "ESP"));
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mediaUri != null) {
            outState.putString("cameraImageUri", mediaUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) {
            mediaUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }

    public void idioma(View view){
        Intent intent = new Intent(this, Idioma.class);
        startActivity(intent);
    }

    public void ubicación(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW,viaje.getLocation());
        startActivity(intent);
    }

    public void turismo(View view){
        Intent intent = new Intent(this,Turismo.class);
        startActivity(intent);
    }

    public void valoracion(View view){
        Intent intent = new Intent(this, Valoracion.class);
        startActivity(intent);
    }

    public void speak(View view){
        String toSpeak = view.getTag().toString();
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }

    public void sacarFoto(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,R.string.no_camera,Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                try{
                    SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
                    String user= prefs.getString("name", "");
                    File file=File.createTempFile("Eurocity"+user,".jpg",dir);
                    mediaUri= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,mediaUri);
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);
                }catch (IOException e){

                }
            }
            else
                Toast.makeText(this, R.string.no_app,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case PICTURE_REQUEST_CODE:
                final String path = mediaUri.getPath();
                if(RestClient.getConnectivity(this)) {
                    try {
                        new ProgressTask<Integer>(this) {
                            @Override
                            protected Integer work() throws Exception {
                                return uploadFile(path);
                            }

                            @Override
                            protected void onFinish(Integer result) {
                                int response=result;
                                Log.i("Respuesta",Integer.toString(response));
                            }
                        }.execute();
                    } catch (Exception e) {
                    }
                }
                else
                    Toast.makeText(this, R.string.no_internet,Toast.LENGTH_SHORT).show();
        }
    }

    public int uploadFile(String fileName){
        rest= new RestClient(getString(R.string.server_url));
        try {
            Foto foto = new Foto();
            SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
            final String user= prefs.getString("name", "");
            final String lugar= prefs.getString("lugar", "");
            foto.setUser(user);
            foto.setLugar(lugar);
            foto.setPath(fileName);
            int response=rest.postJson(foto.toJson(),"addFoto");
            return response;
        }
        catch (Exception e){
            String error= e.toString();
            Log.e("error file",error);
            return -1;
        }

    }
}
