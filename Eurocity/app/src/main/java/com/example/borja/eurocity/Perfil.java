package com.example.borja.eurocity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Perfil extends AppCompatActivity {
    final int PICTURE_REQUEST_CODE=1;

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

    public void sacarFoto(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            Toast.makeText(this,R.string.no_camera,Toast.LENGTH_SHORT).show();
        else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File dir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try{
                    File file=File.createTempFile("tta",".jpg",dir);
                    Uri pictureUri= Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                    startActivityForResult(intent,PICTURE_REQUEST_CODE);
                }catch (IOException e){

                }
            }
            else
                Toast.makeText(this, R.string.no_app,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case PICTURE_REQUEST_CODE:
                Toast.makeText(this,"Foto sacada",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void nuevoViaje(View view){
        Intent intent = new Intent(this, NuevoViaje.class);
        startActivity(intent);
    }
}
