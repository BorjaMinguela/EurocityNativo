package com.example.borja.eurocity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view){
        Intent intent = new Intent(this, Perfil.class);
        String login= ((EditText)findViewById(R.id.login)).getText().toString();
        if(true){//TODO: Implementar autenticación
            intent.putExtra("USERNAME",login);
            startActivity(intent);
        }
    }
}
