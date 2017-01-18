package com.example.borja.eurocity.model;

import android.graphics.Color;
import android.net.Uri;

import com.example.borja.eurocity.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
            case "Lisboa":
                return R.color.LimeGreen;
            case "Londres":
                return R.color.Orange;
            case "Paris":
                return R.color.Yellow;
            case "Roma":
                return R.color.MediumTurquoise;

        }
        return R.color.Maroon;
    }
    public Uri getLocation(){
        switch (nombre){
            case "Lisboa":
                return Uri.parse("geo:38.7436057,-9.2302437");
            case "Londres":
                return Uri.parse("geo:51.5287352,-0.3817868");
            case "Paris":
                return Uri.parse("geo:48.8589507,2.2775166");
            case "Roma":
                return Uri.parse("geo:41.9102415,12.3959112");
        }
        return Uri.parse("geo:0,0");
    }

    public List<String> getLugaresEmblematicos(){
        List<String> lugares = new ArrayList<>();
        switch (nombre){
            case "Lisboa":
                lugares.add("Plaza del comercio");
                lugares.add("Torre de Belém");
                lugares.add("Barrio de la Alfama");
                break;
            case "Londres":
                lugares.add("London Eye");
                lugares.add("Buckingham palace");
                lugares.add("Big Ben");
                break;
            case "Paris":
                lugares.add("Torre Eiffel");
                lugares.add("Catedral de Notre Dame");
                lugares.add("Arco del triunfo");
                break;
            case "Roma":
                lugares.add("Coliseo");
                lugares.add("Fontana de Trevi");
                lugares.add("Foro Romano");
                break;
        }
        return lugares;
    }

    public List<String> getGastronomia(){
        List<String> lugares = new ArrayList<>();
        switch (nombre){
            case "Lisboa":
                lugares.add("Bolinhas de berlim");
                lugares.add("Bacalao");
                lugares.add("Pasteles de belem");
                lugares.add("Francesinha");
                break;
            case "Londres":
                lugares.add("Fish and chips");
                lugares.add("Crepes");
                lugares.add("Pies and kornish pasties");
                lugares.add("Backed/jacket potatoe");
                break;
            case "Paris":
                lugares.add("Ratatouille");
                lugares.add("Macarons");
                lugares.add("Foie grass");
                lugares.add("Omelettes");
                break;
            case "Roma":
                lugares.add("Supplí alla Romana");
                lugares.add("Spagetti alla Carbonara");
                lugares.add("Gelato");
                break;
        }
        return lugares;
    }
}
