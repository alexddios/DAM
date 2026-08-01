let parrafos =document.getElementsByTagName("p")
console.log(parrafos.length)
// Contenido de los parrafos con etiquetas
for (let i=0; i<parrafos.length;i++)
    console.log(`i:${i} - ${parrafos[i].innerHTML}`)

// Contenido de los parrafos sin etiquetas
for (let i=0; i<parrafos.length;i++)
    console.log(`i:${i} - ${parrafos[i].textContent}`)


//2
let titulo = document.getElementById("titol");
titulo.style.fontStyle = "italic"

//3
const nombre = "Alex"
let texto = document.getElementById("missatge")
texto.textContent = `Sóc n' ${nombre} i he canviat el texte d’aquest missatge`

// Recupera tots els elements destacats (classe destacat) i substitueix totes les “a” per “-”.
let links = document.getElementsByClassName("destacat");
for (let i = 0; i < links.length; i++) {
  links[i].innerHTML = links[i].innerHTML.replace(/a/g, "-");
}


//Accedeix al contingut del segon element de la llista i afegeix el texte “fet”
let llista = document.getElementsByTagName("li");
llista[1].textContent += " fet";