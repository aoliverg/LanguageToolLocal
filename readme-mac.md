# Building and Packaging on macOS

This guide explains how to compile the graphical user interface source code (`LanguageToolTrayApp.java`) and generate a self-contained macOS application bundle (`.app` / `.dmg`) with an embedded Java runtime using `jpackage`.

## 1. Prerequisites

You need a **JDK** (version 21 or higher) installed on macOS that includes the `jpackage` tool (such as Eclipse Temurin). You can install it via Homebrew or download the `.pkg` installer from [adoptium.net](https://adoptium.net/):

```bash
brew install --cask temurin@21
```

*(Note: Build on an Apple Silicon Mac (`aarch64`) to produce the Apple Silicon binary, or on an Intel Mac (`x64`) for Intel-based Macs).*

## 2. Preparing the LanguageTool Files

To keep the repository lightweight, the LanguageTool binaries are not included in version control. Before compiling, download the official standalone version of LanguageTool (e.g., `LanguageTool-6.6.zip`) and extract all its contents into a `libs/` folder at the root of the project:

```bash
mkdir -p libs build dist
# Copy languagetool-server.jar along with the libs/, org/, and META-INF/ directories from LanguageTool into libs/
```

## 3. Compiling the Java Code

Open Terminal in the project root directory, compile the main class, and package it into a `.jar` file inside the `libs/` folder:

```bash
javac -encoding UTF-8 -d build src/LanguageToolTrayApp.java
jar cfe libs/LanguageToolTrayApp.jar LanguageToolTrayApp -C build .
```

### Quick Test (Optional)

You can verify that both the interface and the server start properly before creating the final bundle:

```bash
java -Dapple.awt.UIElement=true -cp "libs/LanguageToolTrayApp.jar:libs/languagetool-server.jar" LanguageToolTrayApp
```

## 4. Packaging with `jpackage`

Generate the native macOS `.app` bundle inside a `.dmg` disk image (with the Java runtime bundled inside so end users don't need to install anything):

```bash
rm -rf dist/LanguageToolLocal*

jpackage \
  --input libs \
  --dest dist \
  --name "LanguageToolLocal" \
  --main-jar LanguageToolTrayApp.jar \
  --main-class LanguageToolTrayApp \
  --type dmg \
  --app-version 6.6 \
  --java-options "-Dapple.awt.UIElement=true"
```

*(If you prefer a compressed `.zip` of the `.app` bundle instead of a `.dmg` installer, use `--type app-image` and then compress `dist/LanguageToolLocal.app` with `ditto -c -k --keepParent dist/LanguageToolLocal.app dist/LanguageToolLocal-Mac.zip`).*

## 5. Preparing the File for GitHub Releases

Rename the generated `.dmg` file to indicate the CPU architecture (e.g., `LanguageToolLocal-Mac-AppleSilicon.dmg` or `LanguageToolLocal-Mac-Intel.dmg`) and upload it as an asset to the **Releases** section on GitHub.
