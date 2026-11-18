# Licencias, GitHub Pages y correo de los commits al publicar

- **Origen:** SpriteAnimation, 2026-11-02, al preparar la publicación del repositorio (tarea 8).
- **Contexto:** el repositorio `jumibot/SpriteAnimation` se hace público con un README. Faltaba elegir la licencia, cómo se ve el documento del MVP en la web y qué hacer con el correo personal que llevan los commits.
- **Opciones:** licencias MIT + CC BY 4.0, MIT + CC BY-SA 4.0 o todo CC BY 4.0; activar GitHub Pages o enlazar el .md; dejar el correo personal en el historial o reescribirlo con el noreply de GitHub.
- **Decisión:** el código, con licencia MIT (`LICENSE`); el material de `docs/`, con CC BY 4.0 (`docs/LICENSE`). GitHub Pages se activa desde `main`, y el README enlaza la versión web del documento del MVP. El historial se reescribe con el correo noreply de GitHub antes de publicar, y ese correo se configura para este repositorio. Es provisional: el propietario creará un correo para el proyecto.
- **Consecuencias:** se borra el borrador antiguo de la visión (`docs/specs/vision/_respaldo/`), que contradecía la actual. Reescribir el historial cambia todos los hashes y obliga a un push forzado.
