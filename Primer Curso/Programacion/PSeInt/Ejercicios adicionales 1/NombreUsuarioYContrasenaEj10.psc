Algoritmo NombreUsuarioYContrasenaEj10
	Definir x,y Como Entero;
	Definir a,b Como Caracter;
	x=1234;
	a="admin";
	
	Escribir "Introduce nombre de usuario y contraseña:";
	Escribir  Sin Saltar "Usuario?";
	Leer b;
	
	Si a=b Entonces
		Escribir Sin Saltar "Contraseña?";
		Leer y;
		Si y=x Entonces
			Escribir "Acceso concedido";
		SiNo
			Escribir "Contraseña incorrecta";
		FinSi
	SiNo
		Escribir "Usuario no encontrado";
	FinSi
	
FinAlgoritmo
