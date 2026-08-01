Algoritmo PositivoNegativoParImparEj5
	Definir x Como Real;
	Escribir "Introduce un número y yo te diré si es ....";
	Escribir Sin Saltar "Número? ";
	Leer x;
	Si x>0 Entonces
		Si x%2=0 Entonces
			Escribir "El número es positivo y par";
		SiNo
			Escribir "El número es positivo e impar";
		FinSi
	FinSi
	Si x<0 Entonces
		Si x%2=0 Entonces
			Escribir "El número es negativo y par";
		SiNo
			Escribir "El número es negativo e impar";
		FinSi
	FinSi
	Si x=0 Entonces
		Escribir  "El número es el zero";
	FinSi
FinAlgoritmo
