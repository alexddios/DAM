package ficherosTexto;

import java.io.*;

public class FichTextoLeer {
  public static void main(String[] args) throws IOException {
	//declarar fichero
    File fichero = new 	File("src\\FichTextoLeer.java");
    //crear el flujo de entrada hacia el fichero             
    FileReader fic = new FileReader(fichero);  
    
    int i;
    while ((i = fic.read()) != -1) //se va leyendo un carácter
      System.out.println( (char) i + "==>"+ i);
         
    fic.close(); //cerrar fichero   
    
    //SEGUNDO EJEMPLO
    System.out.println("SEGUNDO EJEMPLO: ");
    fic = new FileReader("src\\FichTextoLeer.java "); 
    char b[]= new char[20]; 
    while ((i = fic.read(b)) != -1)	    
		System.out.println(b); 
    
    fic.close(); //cerrar fichero       
  }
}
