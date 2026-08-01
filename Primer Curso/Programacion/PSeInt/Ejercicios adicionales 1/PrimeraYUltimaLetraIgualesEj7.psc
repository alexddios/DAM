Algoritmo PrimeraYUltimaLetraIgualesEj7
	Definir x,y,z Como Caracter;
	Escribir "Introduce un texto y yo te diré si la primera letra y la última són iguales o no";
	Escribir Sin Saltar "Texto: ";
	Leer x;
	y = Subcadena(x,1,1);
	z =Subcadena(x,Longitud(x),Longitud(x));
	Si y=z Entonces
		Escribir "La primera y la última letra son iguales";
	SiNo
		Escribir "La primera y la última letra no son iguales";
	FinSi
	
FinAlgoritmo
