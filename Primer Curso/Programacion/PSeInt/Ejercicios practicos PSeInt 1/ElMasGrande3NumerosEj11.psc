//El usuario introduce tres números y el programa le dice si el primer número es el mas grande o no
Algoritmo ElMasGrande3NumerosEj11
	Definir x,y,z Como Entero;//Definición de variables
	//Presentación e introducción de números
	Escribir "Escribe tres números y yo te diré si el primer número es el mas grande.";
	Escribir Sin Saltar "Primer número :";
	Leer x;
	Escribir  Sin Saltar "Segundo número :";
	Leer y;
	Escribir Sin Saltar "Tercer número: ";
	Leer z;
	//Procedimiento
	Si x>y & x>z Entonces
		Escribir "El primer número es el más grande";
	SiNo
		Escribir "El primer número no es el más grande";
	FinSi
FinAlgoritmo
