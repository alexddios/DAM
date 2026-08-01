// Ho ficam tot dins lliga per protegir les funcions. 
// Des de la consola, només volem que es puguin executar les funcions de la nostra API:
//    - addEquip
//    - delEquip
//    - addpartit
//    - delPartit
const lliga = (() => {

  // ====== DADES ======
  let equips = [
    { id: 1, nom: "FC Barcelona" },
    { id: 2, nom: "Real Madrid" },
    { id: 3, nom: "RCD Mallorca" },
    { id: 4, nom: "Real Betis" }
  ];

  let partits = [
    { id: 1, localId: 1, visitantId: 2, gl: 2, gv: 0 }
  ];

  // ====== ORDENACIÓ (JA FETA) ======
  function ordenaClassificacio(classificacio) {
    // NO TOCAR
    classificacio.sort((a, b) => {
      if (b.pts !== a.pts) return b.pts - a.pts;

      const dgA = a.gf - a.gc;
      const dgB = b.gf - b.gc;
      if (dgB !== dgA) return dgB - dgA;

      if (b.gf !== a.gf) return b.gf - a.gf;

      return a.nom.localeCompare(b.nom);
    });
  }

  // ====== RENDER ÚNIC ======
      function render() {
        const tbody = document.getElementById("tbodyEquips");
        const ul = document.getElementById("llistaPartits");
        const stats = document.getElementById("stats");

        // Array per gestionar les dades de la classificació
        const classificacio = equips.map(e => ({
          id: e.id,
          nom: e.nom, 
          pj: 0, // partits jugats
          pg: 0, // partits guanyats
          pe: 0, // partits empatats
          pp: 0, // partits perduts
          gf: 0, // gols a favor
          gc: 0, // gols en contra
          dg:0,  // diferencia de gols
          pts: 0 // punts
        }));

        // 1) Recórrer "partits" i sumar estadístiques a local i visitant
        for (const partit of partits) {
          const local = classificacio.find(e => e.id === partit.localId);
          const visitant = classificacio.find(e => e.id === partit.visitantId);
          if (!local || !visitant) continue;

          // Partits jugats
          local.pj++;
          visitant.pj++;

          // Gols a favor / en contra
          local.gf += partit.gl;
          local.gc += partit.gv;

          visitant.gf += partit.gv;
          visitant.gc += partit.gl;

          // Resultat i punts
          if (partit.gl > partit.gv) {
            local.pg++;
            visitant.pp++;
            local.pts += 3;
          } else if (partit.gl < partit.gv) {
            visitant.pg++;
            local.pp++;
            visitant.pts += 3;
          } else {
            local.pe++;
            visitant.pe++;
            local.pts += 1;
            visitant.pts += 1;
          }
        }

        // DG (diferència de gols) un cop acumulat GF/GC
        for (const equip of classificacio) {
          equip.dg = equip.gf - equip.gc;
        }

        // 2) Ordenació (ja feta)
        ordenaClassificacio(classificacio);

        // 3) Pintar classificació (DOM)
        tbody.innerHTML = "";
        for (const equip of classificacio) {
          const tr = document.createElement("tr");
          tr.setAttribute("data-idEquip", equip.id);
          tr.innerHTML = `<td>${classificacio.indexOf(equip)+1}</td><td>${equip.nom}(${equip.id})
        </td><td>${equip.pj}</td><td>${equip.pg}</td>
        <td>${equip.pe}</td><td>${equip.pp}</td><td>${equip.gf}</td>
        <td>${equip.gc}</td><td>${equip.dg}</td><td>${equip.pts}</td>`;
          tbody.appendChild(tr);
        }

        // 4) Pintar partits (DOM)
        ul.innerHTML = "";
        for (const partit of partits) {
          const li = document.createElement("li");
          li.setAttribute("data-idPartit", partit.id);
          li.innerHTML = `(${partit.id})${equips.find(e => e.id === partit.localId).nom} ${partit.gl} - ${partit.gv} ${equips.find(e => e.id === partit.visitantId).nom}`;
          ul.appendChild(li);
        }

        // 5) Pintar stats
        stats.innerHTML = "";
        stats.innerHTML = `<p>Nº equips: ${equips.length}</p><p>Nº partits: ${partits.length}</p><p>Gols totals: ${partits.reduce((acc, p) => acc + p.gl + p.gv, 0)}</p><p>Líder: ${classificacio[0].nom}</p>`;

      }

  // ====== API PÚBLICA (CONSOLA) ======
  function addEquip(nom) {
    if (!nom) return;
    equips.push({id:equips.length+1,nom:nom});
    console.log(equips);
    render(); // Acaba cridant a render per actualitzar el DOM
  }

  function delEquip(idEquip) {
    equips = equips.filter(e => e.id !== idEquip);
    partits = partits.filter(p => p.localId !== idEquip && p.visitantId !== idEquip);
    console.log(equips);
    render(); // Acaba cridant a render per actualitzar el DOM
  }

  function addPartit(idLocal, idVisitant, gl, gv) {

    if (idLocal === idVisitant) return;
    if (gl < 0 || gv < 0) return;
    if (!equips.find(e => e.id === idLocal) || !equips.find(e => e.id === idVisitant)) return;
    partits.push({id:partits.length+1,localId:idLocal,visitantId:idVisitant,gl:gl,gv:gv});
    console.log(partits);
    render(); // Acaba cridant a render per actualitzar el DOM
  }

  function delPartit(idPartit) {
    if (!partits.find(p => p.id === idPartit)) return;
    partits = partits.filter(p => p.id !== idPartit);
    console.log(partits);
    render(); // Acaba cridant a render per actualitzar el DOM
  }

  render(); // Feim un render inicial per fer una primera càrrega

  return { addEquip, delEquip, addPartit, delPartit };
})();

window.lliga = lliga;
