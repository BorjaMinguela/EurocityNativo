package com.example.borja.eurocity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        String login="";
        if(savedInstanceState==null){
            Bundle extras=getIntent().getExtras();
            if(extras==null){
                login="Problema con el usuario";
            }
            else{
                login=extras.getString("USERNAME");
            }
        }
        else{
            login=(String)savedInstanceState.getSerializable("USERNAME");
        }
        TextView username=(TextView)findViewById(R.id.userName);
        username.setText(login);


    }
}
