package com.example.borja.eurocity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.borja.eurocity.model.Viaje;

public class Turismo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turismo);
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        String lugar= prefs.getString("lugar", "");
        Viaje viaje=new Viaje(lugar);
        ((Button) findViewById(R.id.gastronomia_btn)).setTextColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.lugaresEmblematicos_btn)).setTextColor(getResources().getColor(viaje.getColor()));
    }

    public void gotoLugar(View view){
        Intent intent = new Intent(this,LugaresEmblematicos.class);
        startActivity(intent);
    }

    public void gotoComida(View view){
        Intent intent = new Intent(this,Gastronomia.class);
        startActivity(intent);
    }
}
