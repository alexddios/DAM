package com.alex.repositorio;

import com.alex.modelo.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PeliculaJsonRepository {
    private final File archivo;
    private final ObjectMapper mapper;

    public PeliculaJsonRepository(String ruta){
        this.archivo= new File(ruta);
        this.mapper = new ObjectMapper();
    }
    public Catalogo cargar() throws IOException {
        if(!existe()){
            throw new FileNotFoundException("El fichero no existe, transforma primero el XML a JSON");
        }
        return mapper.readValue(archivo, Catalogo.class);
    }
    public void guardar(Catalogo catalogo) throws IOException{
        mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, catalogo);

    }
    public boolean existe(){
        return archivo.exists() && archivo.length()>0;
    }
}
