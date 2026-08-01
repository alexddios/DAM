//El usuario introduce un número y el programa le dice si es divisible entre 3 y 5 o no
Algoritmo Divisible3y5Ej3
	Definir x Como Entero;	//Definición de variable
	//Presentación e introducción de texto
	Escribir "Introduce un número y yo te diré si es divisible entre 3 y 5";
	Escribir  Sin Saltar "Número? ";
	Leer x;
	//Procedimiento
	Si x%3 =0 & x%5=0 Entonces
		Escribir "El número es divisible entre 3 y 5";
	SiNo
		Escribir "El número no es divisible entre 3 y 5";
	FinSi
FinAlgoritmo
