# Building and Packaging on Linux

This guide explains how to compile the graphical user interface source code (`LanguageToolTrayApp.java`) and generate a self-contained portable package with an embedded Java runtime using `jpackage`.

## 1. Prerequisites

You need a **JDK** (version 21 or higher) installed that includes the `jpackage` tool. On Debian/Ubuntu-based systems:

```bash
sudo apt update
sudo apt install openjdk-21-jdk fakeroot binutils
```

## 2. Preparing the LanguageTool Files

To keep the repository lightweight, the LanguageTool binaries are not included in version control. Before compiling, download the official standalone version of LanguageTool (e.g., `LanguageTool-6.6.zip`) and extract all its contents into a `libs/` folder at the root of the project:

```bash
mkdir -p libs build dist
# Copy languagetool-server.jar along with the libs/, org/, and META-INF/ directories from LanguageTool into libs/
```

## 3. Compiling the Java Code

Compile the main class and package it into a `.jar` file inside the same `libs/` folder:

```bash
javac -encoding UTF-8 -d build src/LanguageToolTrayApp.java
jar cfe libs/LanguageToolTrayApp.jar LanguageToolTrayApp -C build .
```

### Quick Test (Optional)

You can verify that both the interface and the server start properly before creating the final bundle:

```bash
java -cp "libs/LanguageToolTrayApp.jar:libs/languagetool-server.jar" LanguageToolTrayApp
```

## 4. Portable Packaging with `jpackage`

Generate the self-contained application folder (with the Java runtime bundled inside so end users don't need to install anything):

```bash
rm -rf dist/LanguageToolLocal
jpackage \
  --input libs \
  --dest dist \
  --name "LanguageToolLocal" \
  --main-jar LanguageToolTrayApp.jar \
  --main-class LanguageToolTrayApp \
  --type app-image \
  --app-version 6.6
```

## 5. Preparing the File for GitHub Releases

Create a direct symlink at the root so users can easily find the launcher, and compress the entire `dist/LanguageToolLocal` directory:

```bash
cd dist/LanguageToolLocal
ln -s bin/LanguageToolLocal Inicia_LanguageTool
cd ..
tar -czvf LanguageToolLocal-Linux.tar.gz LanguageToolLocal/
```

The resulting `dist/LanguageToolLocal-Linux.tar.gz` file is now ready to be uploaded as an asset to the **Releases** section on GitHub.