// Modo claro por defecto: si el lector no ha elegido modo en «Lectura», el documento empieza en claro
// en lugar de seguir el modo del sistema. El kit de documentos de la SF aún no tiene esta opción.
(function () {
  try {
    var api = window.herramientasDocumento;
    var guardadas = api ? api.leer('documento-lectura:' + api.ruta, {}) : null;
    if (!guardadas || !guardadas.modo) document.documentElement.dataset.modo = 'claro';
  } catch (e) { /* sin almacenamiento: se queda el modo del kit */ }
})();
