Algoritmo PrecioVideojuego
	Definir precio_videojuego,dinero_obtenido Como Real;
	Escribir Sin Saltar "Precio del videojuego?";
	Leer precio_videojuego;
	Escribir Sin Saltar "Cuaanto dinero tienes?";
	Leer dinero_obtenido;
	Si precio_videojuego<dinero_obtenido Entonces
		Escribir "Puedes comprarlo, te sobran " ,dinero_obtenido-precio_videojuego;
	SiNo
		Escribir "No puedes comprarlo, te faltan: " ,precio_videojuego-dinero_obtenido;
	FinSi
FinAlgoritmo
