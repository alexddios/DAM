Algoritmo Ejercicio1Examen
	Definir a,b,c,d Como Caracter;
	Definir x,y Como Entero;
	x=Aleatorio(100,999);
	
	
	Escribir "Introduce tu nombre";
	Leer a;
	Escribir "Introduce tu DNI";
	Leer b;
	Escribir "Introduce tu Email";
	Leer c;
	Escribir "Introduce tu Número de telefono";
	Leer d;
	Escribir "Bienvenido " ,a ," amb DNI " ,b , " Email " ,c , " y número de teléfono " ,d;
	Escribir "Selecciona una de las opciones";
	Escribir " 1. Vuelo con escala";
	Escribir " 2. Vuelo sin escala";
	Leer y;
	Si y<>2 Entonces
		Escribir "Has elegido vuelo con escala, tu numero de vuelo es: " ,x;
	SiNo
		Escribir "Has elegido vuelo sin escala";
	FinSi
FinAlgoritmo
