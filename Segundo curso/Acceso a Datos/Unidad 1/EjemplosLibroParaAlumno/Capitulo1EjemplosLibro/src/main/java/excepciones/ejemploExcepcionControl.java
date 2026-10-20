package excepciones;
/*
 * Prueba de excepciones 1
 */
public class ejemploExcepcionControl {

	public static void main(String[] args) {
		int nume = 10, denom = 0, cociente;

		try {
			cociente = nume / denom;
			System.out.println("Resultado:" + cociente);
		} catch (ArithmeticException a) {
			System.err.println("getLocalizedMessage():" + a.getLocalizedMessage());
			System.out.println("getLocalizedMessage():" + a.getLocalizedMessage());
			System.err.println("getMessage():" + a.getMessage());
		}

	}//
}//
