package com.example.ejerciciostema33;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.boot.autoconfigure.data.mongo.ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AnuncioRestController {

    private Map<Long, Anuncio> anuncios = new ConcurrentHashMap();
    private AtomicLong ultimoid = new AtomicLong();
/*
    //EJERCICIO 5
    @GetMapping("/anuncio")
    public Anuncio anuncio(){
        return new Anuncio("Alvaro","Nueva práctica","Después de semana santa");
    }
*/
    //EJERCICIO 6

    //EN POSTMAN, tenemos que ir a body, seleccionar raw y en text seleccionar JSON, ahí escribimos el contenido de Anuncio así:
    /*
        [
            "nombre": "El nano es",
            "titulo": "El nanooo",
            "comentario": "No quiero a Barrichelo",
        ]
    */

    @GetMapping("/anuncio")
    public Collection<Anuncio> anuncio(){
        return anuncios.values();
    }
    @PostMapping("/anuncio")
    @ResponseStatus(HttpStatus.CREATED)
    public Anuncio nuevoAnuncio(@RequestBody Anuncio anuncio){
        long id = ultimoid.incrementAndGet();
        anuncio.setId(id);
        anuncios.put(id,anuncio);

        return anuncio;
    }

    //devolver un recurso concreto
    @JsonView(Anuncio.class) //que solo me muestre los parámetros que he seleccionado asi en la clase (igual que esto)
    @GetMapping("/anuncio/{id}")
    public ResponseEntity<Anuncio> getAnuncio(@PathVariable long id){
        Anuncio anuncio = anuncios.get(id);
        if(anuncio != null){
            return new ResponseEntity<>(anuncio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //borrar uno concreto
    @DeleteMapping("/anuncio/{id}")
    public ResponseEntity<Anuncio> borraAnuncio(@PathVariable long id) {
        Anuncio anuncio = anuncios.remove(id);
        if(anuncio != null){
            return new ResponseEntity<>(anuncio, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //actualizar un recurso
    @PutMapping("/anuncio/{id}")
    public ResponseEntity<Anuncio> actualizaAnuncio(@PathVariable long id, @RequestBody Anuncio anuncioActualizado) {
        Anuncio anuncio = anuncios.get(id);
        if(anuncio != null){
            anuncioActualizado.setId(id);
            anuncios.put(id, anuncioActualizado);

            return new ResponseEntity<>(anuncioActualizado, HttpStatus.OK);
         } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
