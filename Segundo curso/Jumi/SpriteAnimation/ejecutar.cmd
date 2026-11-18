@echo off
rem Compila y ejecuta la demo. Las hojas de sprites se leen de src\main\resources.
setlocal
cd /d "%~dp0"
rem La consola, en UTF-8 mientras dura el script, para que se vean las tildes; al final se deja como estaba.
for /f "tokens=2 delims=:" %%c in ('chcp') do set "OLDCP=%%c"
set "OLDCP=%OLDCP: =%"
set "OLDCP=%OLDCP:.=%"
chcp 65001 >nul
set RESULT=1
call "%~dp0compilar.cmd" || goto fin
java -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "out\main;src\main\resources" main.SpriteAnimation
set RESULT=%ERRORLEVEL%
:fin
chcp %OLDCP% >nul
exit /b %RESULT%
