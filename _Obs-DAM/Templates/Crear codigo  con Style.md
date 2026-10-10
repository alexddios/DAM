<%*
// 1. Pedir el título
const titulo = await tp.system.prompt("Título del ejercicio");

// 2. Definir las opciones del menú
const opcionesMenu = ["Python", "Java", "SQL", "HTML", "CSS", "JavaScript", "C++","Mermaid"];
// Definir qué escribe realmente en el bloque para cada opción anterior
const valoresCodigo = ["python", "java", "sql", "html", "css", "javascript", "cpp","mermaid"];

// 3. Mostrar el selector
// (Lo que ves en la lista, Lo que se guarda en la variable)
const lenguaje = await tp.system.suggester(opcionesMenu, valoresCodigo);

// Si cancelas con ESC, usamos 'text' por defecto para que no rompa
const langFinal = lenguaje || "text"; 

// 4. Definir marcador del cursor
const cursorMarker = "%CURSOR%";

// 5. Escribir el bloque
tR += "```" + langFinal + " title:\"" + titulo + "\"\n" + cursorMarker + "\n```";

// 6. Mover el cursor (Lógica del Hook)
tp.hooks.on_all_templates_executed(async () => {
    await new Promise(r => setTimeout(r, 10));
    const leaf = app.workspace.activeLeaf;
    if (leaf) {
        const editor = leaf.view.editor;
        const content = editor.getValue();
        const cursorOffset = content.indexOf(cursorMarker);
        if (cursorOffset !== -1) {
            const cursorPosition = editor.offsetToPos(cursorOffset);
            editor.replaceRange("", cursorPosition, {line: cursorPosition.line, ch: cursorPosition.ch + cursorMarker.length});
            editor.setCursor(cursorPosition);
        }
    }
});
%>