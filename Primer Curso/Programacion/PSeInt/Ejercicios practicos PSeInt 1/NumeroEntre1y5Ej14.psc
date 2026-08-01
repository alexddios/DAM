//El usuario introduce un número y si está entre el 1 y el 5 el valor de la variable X no cambiará
Algoritmo NumeroEntre1y5Ej14
	//Definición de variables
	Definir X Como Logico;
	Definir y Como Entero;
	x = Verdadero;
	//Presentación e introducción del número
	Escribir "Introduce un número y si está entre el 1 y 5 el valor de X no cambiará";
	Escribir Sin Saltar "Número? ";
	Leer y;
	//Procedimiento
	Si y <1 | y>5 Entonces
		x = Falso;
	FinSi
	Escribir "Valor de x: " ,x;
 	
FinAlgoritmo
