#!/bin/sh
# Compila y pasa las pruebas. MouseTest abre la ventana y mueve el ratón: no lo toques mientras tanto.
# Opcional: ./probar.sh carpeta  guarda ahí capturas de la ventana.
set -e
cd "$(dirname "$0")"
./compilar.sh
case "$(uname -s)" in MINGW*|MSYS*|CYGWIN*) S=';' ;; *) S=':' ;; esac
CP="out/main${S}out/test${S}src/main/resources"
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$CP" presenter.PresenterTest
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$CP" model.SpriteSheetTest
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$CP" model.ModelTest
if [ -n "$1" ]; then java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -Dcaptura="$1" -cp "$CP" view.MouseTest; else java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$CP" view.MouseTest; fi
echo "Todas las pruebas correctas."
