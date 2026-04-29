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
3. Select Language tool server - Another server (requires a running LanguageTool server)
4. Write `http://localhost:8081/v2`
5. Save the configuration
6. Now you have Language tools running in your browser

## How to run the server at startup
If you want to run the server when you start your computer:

**Method 1: This method will open a terminal window (which you can minimize) every time you log in.**

1.1 Create a Batch Script:

Open Notepad.

Paste the following code (adjust the path to where you unzipped LanguageTool):

```
@echo off
cd "C:\ruta\a\la\teva\carpeta\LanguageTool"
java -cp languagetool-server.jar org.languagetool.server.HTTPServer --port 8081 --allow-origin "*"
```

Save the file as languagetool_server.bat.

Add to Startup:

Press `Win + R` on your keyboard.

Type shell:startup and hit Enter. This opens the Startup folder.

Right-click and drag your languagetool_server.bat file into this folder and select "Create shortcuts here".

Now, every time you turn on your PC and log in, the server will start automatically.

**Method 2: The "Silent" Way (Task Scheduler)**

If you don't want a terminal window popping up, use the Task Scheduler to run it in the background.

Search for "Task Scheduler" in the Windows Start menu and open it.

Click "Create Basic Task..." on the right sidebar.

Name: LanguageTool Server.
* Trigger: Select "When I log on".
* Action: Select "Start a program".
* Program/script: Browse and select your languagetool_server.bat (created in Method 1).
* Finish: Click Finish.

Pro Tip: To make it truly invisible, right-click the task in the list, go to Properties, and under the "General" tab, select "Run whether user is logged on or not". (Note: This might require your Windows password).
