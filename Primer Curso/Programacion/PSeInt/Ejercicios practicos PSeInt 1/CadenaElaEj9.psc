//Este programa trata de que el usuario introduce un texto y si e la posición 2 a la 4 esta compuesto por "ela" le dice  "El código es correcto", si no "Error de seguridad"
Algoritmo CadenaElaEj9
	
	Definir x,y Como Caracter; //Variable x para introducir el texto y la y para la subcadena
	//Presentación e introducción de texto
	Escribir  Sin Saltar "Introduce un texto y yo te diré si el código es correcto o no: ";
	Leer x;
	y = Subcadena(x,2,4); //Subcadena de la posición 2 a la 4
	//Procedimiento de lo que queremos
	Si y= 'ela' Entonces
		Escribir "El código es correcto";
	SiNo
		Escribir "Error de seguridad";
	FinSi
	
FinAlgoritmo
