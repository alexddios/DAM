const textarea = document.getElementById("brut");
const llistaTasques = document.getElementById("tasques");
const resum = document.getElementById("resum");

const linies = textarea.value.split('\n');

let total = 0;
let fetes = 0;
let pendents = 0;

linies.forEach(linia => {
    const textNet = linia.trim();

    if (!textNet) return;

    const esFeta = textNet.startsWith("[x]") || textNet.startsWith("[X]");
    const esPendent = textNet.startsWith("[ ]");

    if (esFeta || esPendent) {
        total++;

        const li = document.createElement("li");
        const span = document.createElement("span");

        span.className = "text";
        span.textContent = textNet.substring(3).trim();

        if (esFeta) {
            li.classList.add("fet");
            fetes++;
        } else {
            pendents++;
        }

        li.appendChild(span);
        llistaTasques.appendChild(li);
    }
});
textarea.remove();
llistaTasques.classList.remove("hidden");
resum.textContent = `Total: ${total} | Fetes: ${fetes} | Pendents: ${pendents}`;
