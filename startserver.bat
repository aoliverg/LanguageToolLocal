@echo off
set "RUTA_ACTUAL=%~dp0"
"%RUTA_ACTUAL%jdk-25.0.1+8\bin\java.exe" -cp "%RUTA_ACTUAL%languagetool-server.jar" org.languagetool.server.HTTPServer --port 8081 --allow-origin "*"