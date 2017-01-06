package com.example.borja.eurocity.model;

import lombok.Data;

/**
 * Created by borja on 6/01/17.
 */

@Data
public class Comentario {
    private String comentario;
    private String categoria;
    private String user;
    private String lugar;
}
