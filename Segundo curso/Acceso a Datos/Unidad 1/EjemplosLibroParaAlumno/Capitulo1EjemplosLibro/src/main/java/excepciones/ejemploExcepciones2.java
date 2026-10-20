package excepciones;

/*
 * Prueba de excepciones 2
 */
public class ejemploExcepciones2 {
public static void main(String[] args) {   
    String cad1="20", cad2="0", mensaje;
	int nume, denom, cociente;
	int[] arraynum = new int[4];	
	
    try {    
		  arraynum[10]=20;
          nume=Integer.parseInt(cad1);
          denom=Integer.parseInt(cad2);
          cociente=nume/denom;
          mensaje=String.valueOf(cociente);		
    } catch(Exception ex){       
		 System.err.println("toString           => "+ ex.toString()); 
		 System.err.println("getMessage         => " + ex.getMessage()); 
		 System.err.println("getLocalizedMessage=> " + ex.getLocalizedMessage()); 
		 ex.printStackTrace(); 
    } 
	finally {
		 System.out.println("SE EJECUTA SIEMPRE"); 
	}
   
 }//
}//

