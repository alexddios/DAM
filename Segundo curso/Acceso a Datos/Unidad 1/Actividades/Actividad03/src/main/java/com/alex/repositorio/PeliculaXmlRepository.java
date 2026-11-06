package com.alex.repositorio;

import com.alex.modelo.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeliculaXmlRepository {
    private final File archivo;

    public PeliculaXmlRepository(String ruta) {
        this.archivo = new File(ruta);
    }

    public List<Pelicula> cargar() {
        List<Pelicula> peliculas = new ArrayList<>();

        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("Error: no existe peliculas.xml, el programa se cerrará");
            System.exit(1);
        }

        try {
            Document documento = new SAXBuilder().build(archivo);
            Element raiz = documento.getRootElement();

            for (Element elemento : raiz.getChildren("pelicula")) {
                Pelicula pelicula = new Pelicula(
                        Integer.parseInt(elemento.getAttributeValue("id")),
                        elemento.getChildText("nombre"),
                        elemento.getChildText("autor"),
                        Integer.parseInt(elemento.getChildText("anio")),
                        elemento.getChildText("genero"));
                peliculas.add(pelicula);
            }
        } catch (JDOMException | IOException | NumberFormatException e) {
            System.out.println("❌ ERROR CRÍTICO: El archivo 'peliculas.xml' contiene errores de formato (" + e.getMessage() + ").");
            System.exit(1);
        }

        return peliculas;
    }
}
