//El usuario introduce un texto y el programa le dice si su longitud es mayor a 5 carácteres
Algoritmo CadenaMayor5CaracteresEj4
	//Definición de variables
	Definir x Como Caracter;
	Definir y Como Entero;
	
	//Presentación, introducción de texto y asignar la longitud a y
	Escribir Sin Saltar "Introduce un texto y yo te diré si es mayor a 5 carácteres: ";
	Leer x;
	y = Longitud(x);
	//Procedimiento
	Si y>5 Entonces
		Escribir "La longitud es mayor a 5 carácteres";
	SiNo
		Escribir "La longitud es menor a 5 carácteres";
	FinSi
FinAlgoritmo
