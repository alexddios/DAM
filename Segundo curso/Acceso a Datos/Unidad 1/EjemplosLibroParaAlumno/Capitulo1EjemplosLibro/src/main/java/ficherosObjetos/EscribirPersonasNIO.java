package ficherosObjetos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EscribirPersonasNIO {
    public static void main(String[] args) throws IOException {
       
        // Define la ruta del archivo
        Path ruta = Path.of("FichPersonas.dat");
        
        ObjectOutputStream dataOS;
        if(Files.exists(ruta)) {
        	dataOS = new MiObjectOutputStream(Files.newOutputStream(ruta,StandardOpenOption.APPEND));
        } else {
           dataOS = new ObjectOutputStream(Files.newOutputStream(ruta, StandardOpenOption.CREATE));             
        }
        
        // Escribe los objetos         
        dataOS.writeObject(new Persona("Felipe", 30)); 
        dataOS.writeObject(new Persona("Maria", 25));  
        dataOS.close();      
           
    }
}
