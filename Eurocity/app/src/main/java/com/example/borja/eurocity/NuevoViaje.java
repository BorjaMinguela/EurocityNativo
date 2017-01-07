package com.example.borja.eurocity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.borja.eurocity.model.Viaje;

public class NuevoViaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_viaje);
        WebView wView=(WebView)findViewById(R.id.nv_web);
        wView.loadUrl("file:///android_asset/doge.html");
        wView.setBackgroundColor(Color.TRANSPARENT);
        wView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        wView.setInitialScale(1);
        wView.setVerticalScrollBarEnabled(false);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.activity_nuevo_viaje);
        //layout.addView(wView);
    }

    public void viajar(View view){
        Intent intent = new Intent(this, ViajePpal.class);
        //Viaje viaje = new Viaje(view.getTag().toString());
        SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
        editor.putString("lugar", view.getTag().toString());
        editor.commit();
        //intent.putExtra("Viaje",viaje);
        startActivity(intent);
    }
}
