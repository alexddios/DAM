Algoritmo DiasDelMesEj11
	Definir x,y Como Entero;
	Escribir "Introduce el número del més que quieras y yo te diré cuantos diás tiene";
	Escribir Sin Saltar "Número: ";
	Leer x;
	
	Segun x Hacer
		1,3,5,7,8,10,12:
			Escribir "Tiene 31 días";
		4,6,9,11:
			Escribir "Tiene 30 días";
		2:
			Escribir Sin Saltar"Que año?";
			Leer y;
			Si ( (y%4=0) & (y%100 <> 0) ) | (y%400=0) Entonces
				Escribir "Tiene 29 días";
			SiNo
				Escribir "Tiene 28 días";
			FinSi
	FinSegun
	
	
FinAlgoritmo
