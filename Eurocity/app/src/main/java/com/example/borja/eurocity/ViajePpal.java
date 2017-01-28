package com.example.borja.eurocity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.borja.eurocity.model.Viaje;

import java.util.Locale;

public class ViajePpal extends AppCompatActivity {
    private Viaje viaje;
    private TextToSpeech tts;

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
}
