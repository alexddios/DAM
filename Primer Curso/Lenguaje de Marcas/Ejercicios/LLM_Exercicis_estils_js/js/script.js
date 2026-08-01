// Helpers
const $ = (sel) => document.querySelector(sel);

const banner = $("#banner");
const bannerText = $("#bannerText");

const card = $("#card");
const bar = $("#bar");
const pct = $("#pct");

const btnBanner = $("#btnBanner");
const btnBannerReset = $("#btnBannerReset");

const btnTheme = $("#btnTheme");
const btnCardState = $("#btnCardState");

const btnProgress = $("#btnProgress");
const btnShake = $("#btnShake");

let progress = 0;

/*
  EXERCICI 1 — Banner “warning” i reset

  OBJECTIU:
  - En clicar “1) Banner warning”, el banner ha de passar a l’estil WARNING (canvi de colors)
    i el text ha de dir “Mode warning”.
  - En clicar “1) Reset banner”, el banner ha de tornar a l’estil “sense variant” (aspecte neutre)
    i el text ha de dir “Sense variant”.

  PISTA:
  - Revisa al CSS com es defineixen els estils del banner segons “variants”.
*/

btnBanner.addEventListener("click", () => {
  banner.dataset.variant = "warn";
  bannerText.textContent = "Mode warning";
});

btnBannerReset.addEventListener("click", () => {
    banner.removeAttribute("data-variant");
    bannerText.textContent = "Sense variant";
});


/*
  EXERCICI 2 — Mode fosc (toggle)

  OBJECTIU:
  - En clicar “2) Mode fosc (toggle)”, la pàgina ha d’alternar entre:
    (a) mode normal
    (b) mode fosc

  PISTA:
  - Revisa al CSS com s’activa el mode fosc
*/

btnTheme.addEventListener("click", () => {
    document.body.classList.toggle("dark");
});


/*
  EXERCICI 3 — Targeta seleccionada

  OBJECTIU:
  - En clicar “3) Targeta selected”, la targeta ha d’alternar entre:
    (a) estat normal
    (b) estat selected (canvia vora i ombra)

  PISTA:
  - Revisa al CSS quin selector aplica l’estil de selected a la targeta
*/

btnCardState.addEventListener("click", () => {
  card.classList.toggle("selected");
});


/*
  EXERCICI 4 — Barra de progrés (+15%)

  OBJECTIU:
  - En clicar “4) +15% progrés”, el percentatge ha de pujar de 15 en 15 fins a 100.
  - S’ha de veure reflectit:
    (a) l’amplada de la barra
    (b) el número del percentatge

  PISTA:
  - El CSS de la barra ja està preparat perquè una propietat “numèrica” es noti visualment
*/

btnProgress.addEventListener("click", () => {
    progress = Math.min(progress + 15, 100);
    bar.style.width = `${progress}%`;
    pct.textContent = progress;
});


/*
  EXERCICI 5 — Efecte “shake” a la targeta

  OBJECTIU:
  - En clicar “5) Shake targeta”, la targeta s’ha de moure ràpidament:
    esquerra → dreta → centre (efecte sacsejada curt)

  PISTA:
  - Pensa en una propietat que mogui un element “una mica” sense canviar el layout.
    (ex: transform) i combina-la amb setTimeout per fer 2-3 passos.
*/

btnShake.addEventListener("click", () => {
    if (card.dataset.shaking === "1") return; // evita spamear el efecto
    card.dataset.shaking = "1";

    card.style.transform = "translateX(-10px)";
    setTimeout(() => {
        card.style.transform = "translateX(10px)";
    }, 80);

    setTimeout(() => {
        card.style.transform = "translateX(0px)";
        card.dataset.shaking = "0";
    }, 160);
});
