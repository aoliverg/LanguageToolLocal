# LanguageToolLocal
Easy-to-run local version of LanguageTool

## How to download and run the server

1. Download the zip file in the last release:[https://github.com/aoliverg/LanguageToolLocal/releases/tag/v.6.6]
2. Unzip it in any folder of your computer
3. In Windows double-clic in startserver.bat; In Linux or Mac write in terminal `./startserver.sh`
4. In Linux and Mac you need to have java installed; in Windows, the required java version is distributed in the zip and you don't need to install anything.

## How to configure the Google Chrome plugin
1. Open the LanguageTool plugin in Chrome and select Settings. 
2. Go to advanced settings (only for professional users).
3. Select Language tool server - Local server (localhost)
4. Save the configuration
5. Now you have Language tools running in your browser

## How to run language tool in startup in Windows

To make LanguageTool start automatically every time you turn on your computer, follow these simple steps:

**Method 1: Automatic Setup (Recommended)**

This is the easiest way. We have included a script that does all the hard work for you.

Open the folder where you unzipped LanguageTool.

Find the file named `arrencaeniniciar.bat` and double-click it.

That's it! A shortcut has been created in your Windows Startup folder. From now on, the server will start automatically in the background (hidden) whenever you log in.

**Method 2: Manual Shortcut**

If you prefer to do it yourself or the script above doesn't work:

Right-click on the file languagetool_invisible.vbs and select Create shortcut.

Press Win + R on your keyboard, type shell:startup, and hit Enter.

Move the shortcut you just created into this folder.

Pro Tip: By using the .vbs file, the server will run in "silent mode." You won't see any black terminal windows, but the server will be active and ready to check your texts at localhost:8081.
Windows password).
