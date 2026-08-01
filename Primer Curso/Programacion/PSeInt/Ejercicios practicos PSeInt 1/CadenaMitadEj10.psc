//El programa le pregunta si quiere la primera o segunda mitad de su texto o salir del programa, el usuario tiene que elegir y luego introducir su texto y el programa le enseñará la opción que ha elegido
Algoritmo CadenaMitadEj10
	Definir x,y,z Como Caracter; //Definición de variables 
	//x para la opción
	//y para el texto
	//z para la subcadena
	
	//Presentación e introducción de opción y texto
	Escribir "Selecciona una de las siguientes opciones introduciento la letra A o B, cualquier otra cosa para salir del programa";
	Escribir "Opción A : Subcadena de la primera mitad de la frase";
	Escribir "Opción B : Subcadena de la segunda mitad de la frase";
	Escribir Sin Saltar "Opción? ";
	Leer x;
	Escribir Sin Saltar"Introduce el texto :";
	Leer y;
	//Procedimiento
	Segun x Hacer
		"A":
			z= Subcadena(y,0,Longitud(y)/2);
			Escribir "La primera mitad del texto es: "  ,z;
		"B":
			z= Subcadena(y,(Longitud(y)/2)+1,Longitud(y));
			Escribir "La segunda mitad del texto es: "  ,z;
		De Otro Modo:
			Escribir "Saliste del programa";
	FinSegun
	
FinAlgoritmo
