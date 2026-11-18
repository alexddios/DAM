@echo off
rem Compila la demo y las pruebas con javac (Java 21). No hace falta Gradle ni Maven.
setlocal
cd /d "%~dp0"
if exist out rmdir /s /q out
rem Cada paquete es una carpeta: se compilan todas.
set "MAIN=src\main\java"
set "TEST=src\test\java"
javac -encoding UTF-8 -d out\main %MAIN%\main\*.java %MAIN%\model\*.java %MAIN%\view\*.java %MAIN%\presenter\*.java %MAIN%\contract\*.java || exit /b 1
javac -encoding UTF-8 -cp out\main -d out\test %TEST%\check\*.java %TEST%\model\*.java %TEST%\view\*.java %TEST%\presenter\*.java || exit /b 1
echo Compilado en out\
