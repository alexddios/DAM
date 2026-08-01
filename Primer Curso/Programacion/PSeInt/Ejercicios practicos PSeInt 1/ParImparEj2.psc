//El usuario introduce un número y el programa le dice si es par o impar
Algoritmo ParImparEj2
	Definir x como Entero;//Definición de variable
	//Presentación e introducción de número
	Escribir Sin Saltar "Introduce un número: ";
	Leer x;
	//Procedimiento
	Si x=(x%2) Entonces
		Escribir "El número es par";
	SiNo
		Escribir "El número es impar";
	FinSi
FinAlgoritmo
