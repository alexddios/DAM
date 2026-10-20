package ficherosObjetos;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
/**
 * Redefinición de la clase ObjectOuputStream para que no escriba una cabecera
 * al inicio del Stream.
 * @author Javier Abellán.
 * Para no tener problemas con las cabeceras de los objetos y evitar el error  
 * StreamCorruptedException, creamos una clase con nuestro propio 
 * ObjectOutputStream, heredando del original y redefiniendo el método 
 * writeStreamHeader() vacío, para que no haga nada.
 */

public class MiObjectOutputStream extends ObjectOutputStream
{
    /** Constructor que recibe OutputStream */
    public MiObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }
    /** Constructor sin parametros */
    protected MiObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }
    /** Redefinicion del metodo de escribir la cabecera para que no haga nada. */
    protected void writeStreamHeader() throws IOException
    {
    }
}
