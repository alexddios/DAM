Algoritmo AnoBisiestoEj6
	Definir x Como Entero;
	Escribir "Escribeme un año y yo te diré si es bisiesto o no";
	Escribir Sin Saltar "Año? ";
	Leer x;
	Si ( (x%4=0) & (x%100 <> 0) ) | (x%400=0) Entonces
		Escribir "Es un año bisiesto";
	SiNo
		Escribir "No es un año bisiesto";
	FinSi
	
	
FinAlgoritmo
