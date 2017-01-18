package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.borja.eurocity.model.Viaje;

public class LugaresEmblematicos extends AppCompatActivity {
    private Viaje viaje;

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
            group.addView(textView);
        }
    }
}
