#!/bin/sh
# Compila la demo y las pruebas con javac (Java 21). No hace falta Gradle ni Maven.
set -e
cd "$(dirname "$0")"
rm -rf out
# Cada paquete es una carpeta: */*.java coge las clases de todas.
javac -encoding UTF-8 -d out/main src/main/java/*/*.java
javac -encoding UTF-8 -cp out/main -d out/test src/test/java/*/*.java
echo "Compilado en out/"
