//El usuario introduce dos números y el programa le dice si los dos son positos o al menos uno no es negativo
Algoritmo DosNumerosPositivoNegativoEj5
	Definir x,y Como Real;//Definición de variables, reales para aceptar positivos y negativos
	//Presentación e introducción de los dos números
	Escribir "Introduce dos números y yo te diré si los dos son positivos o al menos uno no es positivo";
	Escribir Sin Saltar "Primer número: ";
	Leer x;
	Escribir Sin Saltar "Segundo número: ";
	Leer y;
	//Procedimiento
	Si x>=0 & y>=0 Entonces
		Escribir "Los dos números son positivos";
	SiNo
		Escribir "Al menos uno no es positivo";
	FinSi
FinAlgoritmo
