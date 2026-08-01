Algoritmo PequenaCalculadoraEj8
	Definir x,y Como Real;
	Definir z Como Caracter;
	Escribir "Introduce dos números y una de las siguientes opciones:";
	Escribir "+ Sumar";
	Escribir "- Restar";
	Escribir "* Multiplicar";
	Escribir "/ Dividir";
	Escribir Sin Saltar "Primer número:";
	Leer x;
	Escribir Sin Saltar "Segundo número:";
	Leer y;
	Escribir Sin Saltar "Elige opción: ";
	Leer z;
	Segun z Hacer
		"+":
			Escribir "Resultado: " ,x+y;
		"-":
			Escribir "Resultado: " , x-y;
		"*":
			Escribir "Resultado: " ,x*y;
		"/":
			Si y<>0 Entonces
				Escribir "Resultado: " , x/y;
			SiNo
				Escribir "Error, división entre 0";
			FinSi
	FinSegun
	
	
FinAlgoritmo
