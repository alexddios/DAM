//El usuario introduce 3 números y el programa los ordena de mayor a menor
Algoritmo Ordenar3NumerosEj13
    Definir x, y, z, aux Como Real; // 'aux' es una variable auxiliar para intercambiar valores
	

    Escribir Sin Saltar "Introduce el primer número:";
    Leer x;
    Escribir Sin Saltar "Introduce el segundo número:";
    Leer y;
    Escribir Sin Saltar "Introduce el tercer número:";
    Leer z;
	
    // Aseguramos que 'x' sea el mayor de los tres
    Si y > x Entonces
        aux = x;
        x = y;
        y = aux;
    FinSi;
    
    Si z > x Entonces
        aux = x;
        x = z;
        z = aux;
    FinSi;
    
    // Ahora 'x' ya es el mayor. Solo queda ordenar 'y' y 'z'.
    Si z > y Entonces
        aux = y;
        y = z;
        z = aux;
    FinSi;
    
    //Mostrar el resultado
    Escribir "Los números ordenados de mayor a menor son:";
    Escribir x, ", ", y, ", ", z;
FinAlgoritmo