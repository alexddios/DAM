//El usuario introduce dos números y el programa le dice si los dos son impares o al menos uno es par.
Algoritmo DosNumerosParEImparEj8
	Definir x,y Como Real; //Definición de las variables
	//Presentación e introducción de los dos números
	Escribir "Introduce dos números y yo te diré si los dos son impares,o al menos uno es par";
	Escribir Sin Saltar "Primer número: ";
	Leer x;
	Escribir Sin Saltar "Segundo número: ";
	Leer y;
	//Procedimiento
	Si x%2<>0 & y%2<>0 Entonces
		Escribir "Los dos números son impares";
	SiNo
		Escribir "Al menos uno es par";
	FinSi
	
FinAlgoritmo
