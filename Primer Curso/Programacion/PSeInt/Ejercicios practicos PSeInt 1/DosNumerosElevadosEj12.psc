//El usuario introduce dos números y el programa le dice si el primer número elevado al segundo es mayor que el segundo número elevado al primero
Algoritmo DosNumerosElevadosEj12
	Definir x,y Como Entero; //Definimos dos variables enteras para introducir los dos números
	
	//Presentación e introducción de números
	Escribir "Introduce dos números y yo te diré si el primer número elevado al segundo es mayor que el segundo número elevado al primero";
	Escribir  Sin Saltar "Primer numero: ";
	Leer x;
	Escribir Sin Saltar "Segundo número: ";
	Leer y;
	//Proceso para saber lo que necesitamos
	Si x^y > y^x Entonces
		Escribir "El primer número elevado al segundo ("  ,x^y  ,")  es mayor que el segundo número elevado al primero ( " ,y^x  ,")" ;
	SiNo
		Escribir "El primer número elevado al segundo ("  ,x^y  ,") no es mayor que el segundo número elevado al primero ( " ,y^x ,")";
	FinSi
	
FinAlgoritmo
