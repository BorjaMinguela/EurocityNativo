package com.example.borja.eurocity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.borja.eurocity.model.RestClient;
import com.example.borja.eurocity.model.ValoracionModel;

import org.json.JSONObject;

public class Valoracion extends AppCompatActivity {
    private RestClient rest;
    private ValoracionModel val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracion);
        Spinner spin=(Spinner) findViewById(R.id.valoracion_spinner);
        Integer[] notas = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,R.layout.spinnerlayout, notas);
        spin.setAdapter(adapter);
    }

    public void valorar(View view){
        final int valoracion = Integer.parseInt(((Spinner) findViewById(R.id.valoracion_spinner)).getSelectedItem().toString());
        SharedPreferences prefs = getSharedPreferences("USER", MODE_PRIVATE);
        final String login= prefs.getString("name", "");
        final String lugar= prefs.getString("lugar", "");
        val=new ValoracionModel(login,lugar,valoracion);
        if(RestClient.getConnectivity(this)) {
            try {
                new ProgressTask<Integer>(this) {
                    @Override
                    protected Integer work() throws Exception {
                        int response=uploadValoracion(login,valoracion,lugar);
                        return response;
                    }

                    @Override
                    protected void onFinish(Integer result) {
                        endValoracion();
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

    public int uploadValoracion(String nombre,int nota,String lugar){
        rest= new RestClient(getString(R.string.server_url));
        try {
            JSONObject json = val.toJson();
            int result=rest.postJson(json,"addValoracion");
            Log.i("Valoracion response",Integer.toString(result));
            return result;

        }
        catch (Exception e){
            return -1;
        }

    }

    public void endValoracion(){
        ((Button) findViewById(R.id.valoracion_bt)).setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Gracias por su valoración",Toast.LENGTH_SHORT).show();
    }
}
