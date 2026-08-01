const paralues = ["aa?", "bb?", "cc?", "dd?"];
const resultat = [];

for (let i = 0; i < paralues.length; i++) {
  resultat.push(paralues[i].replace("?", "."));
}

console.log(resultat); // [ 'aa.', 'bb.', 'cc.', 'dd.' ]
