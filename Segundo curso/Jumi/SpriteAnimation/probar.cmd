@echo off
rem Compila y pasa las pruebas. MouseTest abre la ventana y mueve el raton: no lo toques mientras tanto.
rem Opcional: probar.cmd C:\carpeta  guarda ahi capturas de la ventana.
setlocal
cd /d "%~dp0"
rem La consola, en UTF-8 mientras dura el script, para que se vean las tildes; al final se deja como estaba.
for /f "tokens=2 delims=:" %%c in ('chcp') do set "OLDCP=%%c"
set "OLDCP=%OLDCP: =%"
set "OLDCP=%OLDCP:.=%"
chcp 65001 >nul
set "UTF8=-Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8"
set "CP=out\main;out\test;src\main\resources"
set "CAPTURA="
if not "%~1"=="" set "CAPTURA=-Dcaptura=%~1"
set RESULT=1
call "%~dp0compilar.cmd" || goto fin
java %UTF8% -cp "%CP%" presenter.PresenterTest || goto fin
java %UTF8% -cp "%CP%" model.SpriteSheetTest || goto fin
java %UTF8% -cp "%CP%" model.ModelTest || goto fin
java %UTF8% %CAPTURA% -cp "%CP%" view.MouseTest || goto fin
echo Todas las pruebas correctas.
set RESULT=0
:fin
chcp %OLDCP% >nul
exit /b %RESULT%
