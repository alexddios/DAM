Algoritmo NotaEj3
	Definir x Como real;
	Escribir "Introduce tu nota y yo te diré si estas suspendido, aprobado, notable o excelente";
	Escribir Sin Saltar "Nota? ";
	Leer x;
	Si x<0 | x>10 Entonces
		Escribir "Nota invalida";
	SiNo
		Si x<5 Entonces
			Escribir "Suspendido";
		SiNo
			Si x<7 Entonces
				Escribir "Aprobado";
			SiNo
				Si x<9 Entonces
					Escribir "Notable";
				SiNo
					Escribir "Excelente";
				FinSi
			FinSi
		FinSi
	FinSi
	
	
FinAlgoritmo
