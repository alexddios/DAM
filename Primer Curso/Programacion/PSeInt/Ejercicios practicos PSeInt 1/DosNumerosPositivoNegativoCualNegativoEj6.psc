//El usuario introduce dos números y el programa le dice si los dos son positivos o que número es negativo
Algoritmo DosNumerosPositivoNegativoCualNegativoEj6
	Definir x,y Como Real;//Definición de variables
	//Presentación e introducción de números
	Escribir "Introduce dos números y yo te diré si los dos son positivos o cual es el negativo";
	Escribir Sin Saltar "Primer número: ";
	Leer x;
	Escribir Sin Saltar "Segundo número: ";
	Leer y;
	//Procedimiento
	Si x>=0 & y>=0 Entonces
		Escribir "Los dos números son positivos";
	SiNo
		Si x<0 Entonces
			Escribir "El número negativo es el primero";
		SiNo
			Escribir "El número negativo es el segundo";
		FinSi
	FinSi
FinAlgoritmo
