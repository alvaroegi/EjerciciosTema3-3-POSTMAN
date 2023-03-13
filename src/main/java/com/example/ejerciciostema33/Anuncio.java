package com.example.ejerciciostema33;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //constructor con todos los parámetros
@NoArgsConstructor //constructor sin parámetros
public class Anuncio {
    @JsonView(Anuncio.class)
    private String nombre;
    private String titulo;
    private String comentario;

    //EJERCICIO 6
    private long id = -1;
    //constructor sin id
    public Anuncio(String name, String title, String comment){
        nombre = name;
        titulo = title;
        comentario = comment;
    }
}
