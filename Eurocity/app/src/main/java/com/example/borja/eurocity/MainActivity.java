package com.example.borja.eurocity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.borja.eurocity.model.ProgressTask;
import com.example.borja.eurocity.model.RestClient;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RestClient rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view){
        if(RestClient.getConnectivity(this)) {
            final Intent intent = new Intent(this, Perfil.class);
            final String login = ((EditText) findViewById(R.id.login)).getText().toString().trim();
            try {
                new ProgressTask<Boolean>(this) {
                    @Override
                    protected Boolean work() throws Exception {
                        boolean response=log2Server(login);
                        return response;
                    }

                    @Override
                    protected void onFinish(Boolean result) {
                        if (result) {
                            SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                            editor.putString("name", login);
                            editor.commit();
                            startActivity(intent);
                        }
                        else{
                            ((EditText) findViewById(R.id.login)).setTextColor(Color.RED);
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

    public void register(View view){
        if(RestClient.getConnectivity(this)) {
            final Intent intent = new Intent(this, Perfil.class);
            final String login = ((EditText) findViewById(R.id.login)).getText().toString();
            try {
                new ProgressTask<Boolean>(this) {
                    @Override
                    protected Boolean work() throws Exception {
                        boolean response=register2Server(login);
                        return response;
                    }

                    @Override
                    protected void onFinish(Boolean result) {
                        if (result) {
                            SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                            editor.putString("name", login);
                            editor.commit();
                            startActivity(intent);
                        }
                        else{
                            ((EditText) findViewById(R.id.login)).setTextColor(Color.RED);
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

    public boolean log2Server(String user){
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestUser?userName=%s", user));
            String nombre=json.getString("nombre");
            if(nombre.equals(user))
                return true;
            //else
            //  Toast.makeText(this, "No existe el usuario con nombre: "+user,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            String error=e.toString();
            Log.e("Error JSON",error);
            return false;
        }
        return false;
    }

    public boolean register2Server(String user){
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = rest.getJson(String.format("requestUser?userName=%s", user));
            String nombre=json.getString("nombre");
            if(!nombre.equals(user)) {
                JSONObject userJson = new JSONObject();
                userJson.put("nombre", user);
                int result=rest.postJson(userJson,"addUser");
                Log.i("addUser response",Integer.toString(result));
                return true;
            }
            //else
            //  Toast.makeText(this, "No existe el usuario con nombre: "+user,Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            String error=e.toString();
            Log.e("Error JSON",error);
            return false;
        }
        return false;
    }
}
