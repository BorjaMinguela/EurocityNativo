package com.example.borja.eurocity.model;

import android.graphics.Color;
import android.net.Uri;

import com.example.borja.eurocity.R;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by borja on 4/01/17.
 */

@Data
public class Viaje  implements Serializable {
    private String nombre;

    public Viaje(String nombre){
        this.setNombre(nombre);
    }

    public int getColor() {
        switch (nombre){
            case "lisboa":
                return R.color.LimeGreen;
            case "londres":
                return R.color.Orange;
            case "paris":
                return R.color.Yellow;
            case "roma":
                return R.color.MediumTurquoise;

        }
        return R.color.Maroon;
    }
    public Uri getLocation(){
        switch (nombre){
            case "lisboa":
                return Uri.parse("geo:38.7436057,-9.2302437");
            case "londres":
                return Uri.parse("geo:51.5287352,-0.3817868");
            case "paris":
                return Uri.parse("geo:48.8589507,2.2775166");
            case "roma":
                return Uri.parse("geo:41.9102415,12.3959112");
        }
        return Uri.parse("geo:0,0");
    }
}
