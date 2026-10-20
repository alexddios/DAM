package excepciones;
/*
 * Prueba de excepciones 2
 */
public class ejemploExcepciones {
	public static void main(String[] args) {
		String cad1 = "20", cad2 = "0", mensaje;
		int nume, denom, cociente;
		int[] arraynum = new int[4];
		try {
			// código que puede producir errores
			arraynum[10] = 20;             // sentencia que produce la excepción
			nume = Integer.parseInt(cad1); // no se ejecuta
			denom = Integer.parseInt(cad2); // no se ejecuta
			cociente = nume / denom; // no se ejecuta
			mensaje = String.valueOf(cociente);// no se ejecuta
		} catch (NumberFormatException ex) {
			mensaje = "Caracteres no numéricos";
		} catch (ArithmeticException ex) {
			mensaje = "Division por cero";
		} catch (ArrayIndexOutOfBoundsException ex) {
			mensaje = "Fuera de rango en el array";
		} finally {
			System.out.println("SE EJECUTA SIEMPRE");
		}
		System.out.println(mensaje); // sí se ejecuta
		
	}// fin de main

}// fin de la clase
