package excepciones;

/*
 * Prueba de excepciones 5
 */

class MiExcepcion1 extends Exception {
	public MiExcepcion1(String mensaje) {
		super(mensaje);
	}
}

class MiExcepcion2 extends RuntimeException {
	public MiExcepcion2(String mensaje) {
		super(mensaje);
	}
}

public class ejemploThrow3 {
	public static void main(String[] args) {

		try {
			comprobarCodigo(-5);
		} catch (MiExcepcion1 e) {
			System.out.println(e.getMessage());
			;
		}
		comprobarDivision(4, 0);
	}

	private static void comprobarCodigo(int cod) throws MiExcepcion1 {
		if (cod <= 0) {
			throw new MiExcepcion1("El código debe ser mayor que 0");
		}
		System.out.println("Código válido");
	}

	private static void comprobarDivision(int a, int b) {
		if (b == 0) {
			throw new MiExcepcion2("No se puede dividir por 0");
		}
		System.out.println("Resultado: " + a / b);
	}
}
