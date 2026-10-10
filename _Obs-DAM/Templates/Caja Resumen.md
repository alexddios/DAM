<%*
// 1. Diccionario de opciones (Texto del menú : Código de Obsidian)
const calloutTypes = {
  "📝 Nota (Note)": "NOTE",
  "ℹ️ Info (Info)": "INFO",
  "✅ Éxito (Success)": "SUCCESS",
  "⚠️ Advertencia (Warning)": "WARNING",
  "🔥 Peligro (Danger)": "DANGER",
  "💡 Consejo (Tip)": "TIP",
  "📋 Resumen (Summary)": "SUMMARY",
  "❓ Pregunta (Question)": "QUESTION",
  "🐞 Bug (Bug)": "BUG",
  "💬 Cita (Quote)": "QUOTE",
  "🧩 Ejemplo (Example)": "EXAMPLE"
};

// 2. Mostrar menú para elegir el TIPO
const typeKey = await tp.system.suggester(Object.keys(calloutTypes), Object.values(calloutTypes));

// Si el usuario cancela (esc), paramos todo
if (!typeKey) return; 

// 3. Mostrar ventanita para escribir el TÍTULO
const title = await tp.system.prompt("Escribe el título del Callout:");

// 4. Construir y pegar el bloque
// Si el título está vacío, solo pone el tipo. Si tiene texto, lo añade.
const header = title ? `> [!${typeKey}] ${title}` : `> [!${typeKey}]`;

// Imprimimos el resultado y dejamos el cursor en la línea de abajo listo para escribir
tR += `${header}\n> `;
%>