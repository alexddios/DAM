package excepciones;

/*
 * Prueba de excepciones 4
 */
public class ejemploThrow2 {
	
	public static void main(String[] args) {
		dividir(10, 0); // Lanza la excepción	
		System.out.println("No se ejecuta");
	}

	public static void dividir(int a, int b) {
		if (b == 0) {
			throw new ArithmeticException("No se puede dividir por cero");
		}
		System.out.println(a / b);
	}
}
