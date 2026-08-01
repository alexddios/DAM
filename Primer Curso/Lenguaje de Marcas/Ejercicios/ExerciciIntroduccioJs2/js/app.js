const alumnos = ["Ana", "Luis", "Marta", "Sergio"];

const notasPorAlumno = [
    [7, 2, 3, null],
    [10, 9, null, 8],
    [null, 4, 5, 6],
    [3, null, null, 7]
];

const contenedor = document.getElementById("resultado");

let fraseTitulo = "Alumnos: ";

for (let i = 0; i < alumnos.length; i++) {
    fraseTitulo += (i + 1) + " - " + alumnos[i];

    if (i < alumnos.length - 1) {
        fraseTitulo += ", ";
    }
}

const tituloH2 = document.createElement("h2");
tituloH2.textContent = fraseTitulo;
contenedor.appendChild(tituloH2);


for (let i = 0; i < alumnos.length; i++) {
    let nombre = alumnos[i];
    let notasOriginales = notasPorAlumno[i];

    let soloNumeros = [];
    let cantidadNP = 0;

    for (let j = 0; j < notasOriginales.length; j++) {
        if (notasOriginales[j] === null) {
            cantidadNP++;
        } else {
            soloNumeros.push(notasOriginales[j]);
        }
    }

    soloNumeros.sort(function(a, b) {
        return b - a;
    });

    for (let k = 0; k < cantidadNP; k++) {
        soloNumeros.push("np");
    }

    const linea = document.createElement("p");
    linea.textContent = nombre + ": " + soloNumeros.join(", ");
    contenedor.appendChild(linea);
}