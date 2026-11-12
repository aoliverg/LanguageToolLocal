# Building and Packaging on Windows

This guide explains how to compile the graphical user interface source code (`LanguageToolTrayApp.java`) and generate a self-contained portable Windows application with an embedded Java runtime using `jpackage`.

## 1. Prerequisites

You need a **JDK** (version 21 or higher) installed on Windows that includes the `jpackage` tool (such as Eclipse Temurin). You can install it quickly via PowerShell using `winget`:

```powershell
winget install EclipseAdoptium.Temurin.21.JDK
```

*(Restart PowerShell after installation so `javac` and `jpackage` are available in your PATH).*

## 2. Preparing the LanguageTool Files

To keep the repository lightweight, the LanguageTool binaries are not included in version control. Before compiling, download the official standalone version of LanguageTool (e.g., `LanguageTool-6.6.zip`) and extract all its contents into a `libs\` folder at the root of the project:

```powershell
mkdir build, libs, dist
# Copy languagetool-server.jar along with the libs\, org\, and META-INF\ directories from LanguageTool into libs\
```

## 3. Compiling the Java Code

Open PowerShell in the project root directory, compile the main class, and package it into a `.jar` file inside the `libs\` folder:

```powershell
javac -encoding UTF-8 -d build src\LanguageToolTrayApp.java
jar cfe libs\LanguageToolTrayApp.jar LanguageToolTrayApp -C build .
```

### Quick Test (Optional)

You can verify that both the interface and the server start properly (using `javaw` to avoid opening a console window) before creating the final bundle:

```powershell
javaw -cp "libs\LanguageToolTrayApp.jar;libs\languagetool-server.jar" LanguageToolTrayApp
```

## 4. Portable Packaging with `jpackage`

Generate the self-contained Windows application folder (with the Java runtime bundled inside so end users don't need to install anything):

```powershell
if (Test-Path dist\LanguageToolLocal) { Remove-Item -Recurse -Force dist\LanguageToolLocal }

jpackage `
  --input libs `
  --dest dist `
  --name "LanguageToolLocal" `
  --main-jar LanguageToolTrayApp.jar `
  --main-class LanguageToolTrayApp `
  --type app-image `
  --app-version 6.6
```

## 5. Preparing the File for GitHub Releases

Compress the entire `dist\LanguageToolLocal` directory into a `.zip` file:

```powershell
Compress-Archive -Path dist\LanguageToolLocal -DestinationPath dist\LanguageToolLocal-Windows.zip -Force
```

The resulting `dist\LanguageToolLocal-Windows.zip` file contains `LanguageToolLocal.exe` and is ready to be uploaded as an asset to the **Releases** section on GitHub.
