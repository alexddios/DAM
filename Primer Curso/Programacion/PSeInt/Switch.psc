Algoritmo Switch
	Definir  x,y,z como entero;
	
	Escribir "Elige una opción:";
	Escribir "1. Multiplicar";
	Escribir "2. Dividir";
	Escribir "3. Sumar";
	Escribir "4. Restar";
	Escribir "5 Par o impar";
	Leer x;
	
	Escribir "Introduce dos números: ";
	Escribir Sin Saltar "Número uno:";
	Leer y;
	Escribir sin saltar "Número dos:";
	Leer z;
	
	Segun x Hacer
		1:
			Escribir "La multiplicación de los numeros es " ,y*z;
		2:
			Escribir "La división de los números es " ,y/z;
		3:
			Escribir "La suma de los números es " ,y+z;
		4:
			Escribir "La resta de los números es " ,y;
		5:
			Si y%2 =0 Entonces
				Escribir y ,"Es par";
			SiNo
				Escribir y ,"Es impar";
			FinSi
			Si z%2 =0 Entonces
				Escribir z ," Es par";
			SiNo
				Escribir z ," Es impar";
			FinSi
		De Otro Modo:
			Escribir "Te he dicho del 1 al 5";
	FinSegun
	
FinAlgoritmo
