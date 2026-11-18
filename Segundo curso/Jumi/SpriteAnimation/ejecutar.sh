#!/bin/sh
# Compila y ejecuta la demo. Las hojas de sprites se leen de src/main/resources.
set -e
cd "$(dirname "$0")"
./compilar.sh
# En Windows (Git Bash), java separa el classpath con ';'; en Linux y macOS, con ':'.
case "$(uname -s)" in MINGW*|MSYS*|CYGWIN*) S=';' ;; *) S=':' ;; esac
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "out/main${S}src/main/resources" main.SpriteAnimation
