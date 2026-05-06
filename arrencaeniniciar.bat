@echo off
setlocal

:: Definir rutes
set "RUTA_CARPETA=%~dp0"
set "RUTA_VBS=%~dp0languagetool_server_invisible.vbs"
set "CARPETA_STARTUP=%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup"

echo Instal·lant drecera a: %CARPETA_STARTUP%

:: Creem la drecera amb PowerShell definint el Directori de Treball (WorkingDirectory)
powershell -Command "$s=(New-Object -COM WScript.Shell).CreateShortcut('%CARPETA_STARTUP%\LanguageTool.lnk'); $s.TargetPath='wscript.exe'; $s.Arguments='\"%RUTA_VBS%\"'; $s.WorkingDirectory='%RUTA_CARPETA%'; $s.Save()"

echo.
echo FET! La drecera s'ha configurat correctament.
echo Ara Windows sabra trobar els fitxers dins de: %RUTA_CARPETA%
pause