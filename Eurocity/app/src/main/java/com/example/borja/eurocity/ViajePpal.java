package com.example.borja.eurocity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.borja.eurocity.model.Viaje;

public class ViajePpal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaje_ppal);
        Viaje viaje= (Viaje) getIntent().getSerializableExtra("Viaje");
        ((Button) findViewById(R.id.btn1)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn2)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn3)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn4)).setBackgroundColor(getResources().getColor(viaje.getColor()));
        ((Button) findViewById(R.id.btn5)).setBackgroundColor(getResources().getColor(viaje.getColor()));


    }
}
