# LanguageToolLocal

🌐 **Idioma / Language:** [Català](#català) | [English](#english) | [Español](#español)

![LanguageToolLocal Screenshot](screenshot.png)

---

## Català

**LanguageToolLocal** és una aplicació d'escriptori lleugera i fàcil d'utilitzar que posa en marxa un servidor local de [LanguageTool](https://languagetool.org/) al teu ordinador amb un sol clic, sense necessitat d'instal·lar Java ni configurar fitxers manualment.

### 1. Descàrrega i arrencada (Windows, macOS i Linux)

1. Ves a l'apartat de [**Releases**](https://github.com/aoliverg/LanguageToolLocal/releases) i descarrega el paquet corresponent al teu sistema operatiu:
   - **Windows:** `LanguageToolLocal-Windows.zip`
   - **macOS:** `LanguageToolLocal-Mac-AppleSilicon.dmg` (Macs amb xip M1/M2/M3/M4) o `LanguageToolLocal-Mac-Intel.dmg` (Macs antics amb Intel)
   - **Linux:** `LanguageToolLocal-Linux.tar.gz`

2. **Com posar-lo en marxa:**
   - **Windows:** Descomprimeix el fitxer `.zip` en una carpeta (per exemple, dins de *Documents* o *Programes*) i fes doble clic sobre **`LanguageToolLocal.exe`**.

     > **⚠️ Nota per a usuaris de Windows (SmartScreen / ordinadors d'empresa):**
     > * En un ordinador personal, si apareix l'avís de Windows SmartScreen, fes clic a **"Més informació"** -> **"Executa igualment"**.
     > * En **ordinadors d'empresa** on no aparegui l'opció *"Executa igualment"*, abans o després de descomprimir, fes clic amb el **botó dret** sobre `LanguageToolLocal-Windows.zip` (o sobre `LanguageToolLocal.exe`) -> **Propietats**, marca la casella **Desbloca** a la part inferior de la pestanya *General* i fes clic a **Aplica** / **D'acord**. *(També pots obrir PowerShell dins la carpeta i executar `Get-ChildItem -Recurse | Unblock-File`)*.

   - **macOS:** Obre el fitxer `.dmg`, arrossega **`LanguageToolLocal`** a la carpeta *Aplicacions* i obre'l.

     > **⚠️ Nota per a usuaris de macOS (primer inici):** Com que l'aplicació no està signada amb un certificat comercial d'Apple, macOS la bloquejarà la primera vegada amb l'avís *"no s'ha pogut verificar el desenvolupador"*. Pots autoritzar-la amb qualsevol d'aquestes dues opcions:
     > * **Opció 1 (Des de la Configuració del Sistema — recomanada):**
     >   1. Fes clic a **Fet** (o *Cancel·la*) a l'avís de bloqueig.
     >   2. Obre **Configuració del Sistema** -> **Privadesa i seguretat**.
     >   3. Baixa fins a l'apartat **Seguretat** i fes clic a **Obre igualment** al costat del missatge sobre *LanguageToolLocal*.
     > * **Opció 2 (Amb clic dret al Finder):**
     >   1. Obre la carpeta **Aplicacions**, fes clic amb el **botó dret** (o `Control` + clic) sobre **`LanguageToolLocal`** i tria **Obre**.
     >   2. A la finestra emergent, fes clic al botó **Obre** per confirmar.
     >
     > *(Si encara persisteix el bloqueig, obre el Terminal i executa: `xattr -cr /Applications/LanguageToolLocal.app`)*.

   - **Linux:** Descomprimeix el fitxer `.tar.gz` i fes doble clic sobre **`Inicia_LanguageTool`** (o executa `./LanguageToolLocal/bin/LanguageToolLocal` des del terminal).

3. **Ús de l'aplicació:**
   - En obrir-se, el servidor arrenca automàticament a l'adreça `http://localhost:8081`.
   - Pots tancar la finestra principal amb la **X**: l'aplicació continuarà funcionant discretament en segon pla a la safata del sistema (al costat del rellotge) o a la barra superior de Mac.
   - Marca la casella **"Arrenca automàticament en encendre l'ordinador"** si vols que s'activi sol cada vegada que iniciïs sessió.

### 2. Configuració als navegadors (Chrome, Firefox, Edge, Brave...)

La configuració és **exactament la mateixa a tots els navegadors web**, ja que tots utilitzen la mateixa extensió oficial de LanguageTool:

1. Instal·la l'extensió oficial de **LanguageTool** per a [Google Chrome](https://chromewebstore.google.com/) o [Mozilla Firefox](https://addons.mozilla.org/) (també funciona a Edge, Brave o Opera).
2. Fes clic a la icona de l'extensió de LanguageTool a la barra del navegador i obre la **Configuració** (icona de l'engranatge ⚙️).
3. Baixa fins a l'apartat **Configuració avançada** (*només per a usuaris professionals*).
4. A l'opció **Servidor de LanguageTool**, selecciona **Servidor local (`localhost`)** (o introdueix manualment `http://localhost:8081/v2`).
5. Desa els canvis. A partir d'ara, totes les correccions es faran al 100% en local i de manera privada al teu ordinador.

### 3. Ús en altres aplicacions d'escriptori

Com que el servidor queda actiu a `http://localhost:8081/v2`, també el pots connectar amb altres programes compatibles amb LanguageTool:

- **LibreOffice (Writer, Impress, Calc):** Ves a *Eines -> Opcions -> Configuració de la llengua -> LanguageTool*. Activa'l, deixa el camp de l'URL base amb `http://localhost:8081/v2` (deixa l'usuari i la clau API en blanc) i prem *D'acord*.
- **Mozilla Thunderbird:** Instal·la el complement oficial *LanguageTool* per al correu i configura el servidor local a les seves opcions.
- **Altres editors:** També és compatible amb connectors de **Obsidian**, **Visual Studio Code** (extensió *LTeX+*) o **TeXstudio**.

### 4. Compilació des del codi font (per a desenvolupadors)

Si vols compilar el projecte tu mateix, consulta les guies específiques:

- [Instruccions per a Linux](readme-linux.md)
- [Instruccions per a Windows](readme-windows.md)
- [Instruccions per a macOS](readme-mac.md)

---
---

## English

**LanguageToolLocal** is a lightweight, user-friendly desktop application that launches a local [LanguageTool](https://languagetool.org/) server on your computer with a single click—no Java installation or manual script configuration required.

### 1. Download and Launch (Windows, macOS, and Linux)

1. Go to the [**Releases**](https://github.com/aoliverg/LanguageToolLocal/releases) section and download the package for your operating system:
   - **Windows:** `LanguageToolLocal-Windows.zip`
   - **macOS:** `LanguageToolLocal-Mac-AppleSilicon.dmg` (M1/M2/M3/M4 Macs) or `LanguageToolLocal-Mac-Intel.dmg` (older Intel Macs)
   - **Linux:** `LanguageToolLocal-Linux.tar.gz`

2. **How to run it:**
   - **Windows:** Extract the `.zip` archive into a folder and double-click **`LanguageToolLocal.exe`**.

     > **⚠️ Note for Windows users (SmartScreen / Corporate PCs):**
     > * On a personal PC, if Windows SmartScreen appears, click **"More info"** -> **"Run anyway"**.
     > * On **corporate/managed PCs** where the *"Run anyway"* option is hidden, before or after extracting, **right-click** on `LanguageToolLocal-Windows.zip` (or `LanguageToolLocal.exe`) -> **Properties**, check the **Unblock** box at the bottom of the *General* tab, and click **Apply** / **OK**. *(Alternatively, open PowerShell in the folder and run `Get-ChildItem -Recurse | Unblock-File`)*.

   - **macOS:** Open the `.dmg` file, drag **`LanguageToolLocal`** into your *Applications* folder, and launch it.

     > **⚠️ Note for macOS users (first launch):** Because the app is not notarized with a paid Apple Developer certificate, macOS Gatekeeper will block it the first time with an *"unidentified developer"* warning. You can allow it using either of these two options:
     > * **Option 1 (From System Settings — recommended):**
     >   1. Click **Done** (or *Cancel*) on the popup dialog.
     >   2. Open **System Settings** -> **Privacy & Security**.
     >   3. Scroll down to the **Security** section and click **Open Anyway** next to the message about *LanguageToolLocal*.
     > * **Option 2 (Right-click in Finder):**
     >   1. Open your **Applications** folder, **right-click** (or `Control` + click) on **`LanguageToolLocal`**, and select **Open**.
     >   2. Click **Open** again in the confirmation dialog.
     >
     > *(If macOS still blocks it, open Terminal and run: `xattr -cr /Applications/LanguageToolLocal.app`)*.

   - **Linux:** Extract the `.tar.gz` archive and double-click **`Inicia_LanguageTool`** (or run `./LanguageToolLocal/bin/LanguageToolLocal` from the terminal).

3. **Using the app:**
   - Upon launch, the server automatically starts at `http://localhost:8081`.
   - Closing the main window (**X**) minimizes the app to the system tray (Windows/Linux) or the top menu bar (macOS) so it keeps running quietly in the background.
   - Check **"Arrenca automàticament en encendre l'ordinador"** (*Start automatically at login*) if you want it to run every time you turn on your computer.

### 2. Browser Extension Setup (Chrome, Firefox, Edge, Brave...)

The configuration process is **identical across all web browsers**, as they all use the same official LanguageTool extension interface:

1. Install the official **LanguageTool** add-on for [Google Chrome](https://chromewebstore.google.com/) or [Mozilla Firefox](https://addons.mozilla.org/) (also compatible with Edge, Brave, and Opera).
2. Click the LanguageTool icon in your browser toolbar and open **Settings** (gear icon ⚙️).
3. Scroll down to **Advanced settings** (*only for professional users*).
4. Under **LanguageTool server**, select **Local server (`localhost`)** (or enter `http://localhost:8081/v2`).
5. Save your settings. Your browser will now check all your texts locally and privately on your own machine.

### 3. Using with Other Desktop Applications

Because the server runs at `http://localhost:8081/v2`, you can also connect it to other desktop software:

- **LibreOffice (Writer, Impress, Calc):** Go to *Tools -> Options -> Language Settings -> LanguageTool*. Enable it, set the Base URL to `http://localhost:8081/v2` (leave username and API key blank), and click *OK*.
- **Mozilla Thunderbird:** Install the official *LanguageTool* extension and point it to the local server in its settings.
- **Other Editors:** Works seamlessly with plugins for **Obsidian**, **Visual Studio Code** (*LTeX+* extension), and **TeXstudio**.

### 4. Building from Source (For Developers)

If you want to build the packages yourself, check the platform-specific guides:

- [Linux Build Instructions](readme-linux.md)
- [Windows Build Instructions](readme-windows.md)
- [macOS Build Instructions](readme-mac.md)

---
---

## Español

**LanguageToolLocal** es una aplicación de escritorio ligera y sencilla que pone en marcha un servidor local de [LanguageTool](https://languagetool.org/) en tu ordenador con un solo clic, sin necesidad de instalar Java ni configurar archivos manualmente.

### 1. Descarga y ejecución (Windows, macOS y Linux)

1. Ve a la sección de [**Releases**](https://github.com/aoliverg/LanguageToolLocal/releases) y descarga el paquete correspondiente a tu sistema operativo:
   - **Windows:** `LanguageToolLocal-Windows.zip`
   - **macOS:** `LanguageToolLocal-Mac-AppleSilicon.dmg` (Mac con chip M1/M2/M3/M4) o `LanguageToolLocal-Mac-Intel.dmg` (Mac antiguos con Intel)
   - **Linux:** `LanguageToolLocal-Linux.tar.gz`

2. **Cómo ponerlo en marcha:**
   - **Windows:** Descomprime el archivo `.zip` en una carpeta y haz doble clic sobre **`LanguageToolLocal.exe`**.

     > **⚠️ Nota para usuarios de Windows (SmartScreen / ordenadores de empresa):**
     > * En un ordenador personal, si aparece el aviso de Windows SmartScreen, haz clic en **"Más información"** -> **"Ejecutar de todas formas"**.
     > * En **ordenadores de empresa** donde no aparezca la opción *"Ejecutar de todas formas"*, antes o después de descomprimir, haz **clic derecho** sobre `LanguageToolLocal-Windows.zip` (o sobre `LanguageToolLocal.exe`) -> **Propiedades**, marca la casilla **Desbloquear** en la parte inferior de la pestaña *General* y haz clic en **Aplicar** / **Aceptar**. *(También puedes abrir PowerShell en la carpeta y ejecutar `Get-ChildItem -Recurse | Unblock-File`)*.

   - **macOS:** Abre el archivo `.dmg`, arrastra **`LanguageToolLocal`** a la carpeta *Aplicaciones* y ábrelo.

     > **⚠️ Nota para usuarios de macOS (primer inicio):** Dado que la aplicación no está firmada con un certificado comercial de Apple, macOS la bloqueará la primera vez con el aviso *"no se puede verificar el desarrollador"*. Puedes autorizarla mediante cualquiera de estas dos opciones:
     > * **Opción 1 (Desde Ajustes del Sistema — recomendada):**
     >   1. Haz clic en **Listo** (o *Cancelar*) en el aviso.
     >   2. Abre **Ajustes del Sistema** -> **Privacidad y seguridad**.
     >   3. Baja hasta el apartado **Seguridad** y haz clic en **Abrir igualmente** junto al mensaje sobre *LanguageToolLocal*.
     > * **Opción 2 (Con clic derecho en el Finder):**
     >   1. Abre la carpeta **Aplicaciones**, haz **clic derecho** (o `Control` + clic) sobre **`LanguageToolLocal`** y selecciona **Abrir**.
     >   2. Haz clic en **Abrir** de nuevo en el cuadro de confirmación.
     >
     > *(Si sigue bloqueada, abre el Terminal y ejecuta: `xattr -cr /Applications/LanguageToolLocal.app`)*.

   - **Linux:** Descomprime el archivo `.tar.gz` y haz doble clic sobre **`Inicia_LanguageTool`** (o ejecuta `./LanguageToolLocal/bin/LanguageToolLocal` desde la terminal).

3. **Uso de la aplicación:**
   - Al abrirse, el servidor arranca automáticamente en `http://localhost:8081`.
   - Si cierras la ventana principal (**X**), la aplicación seguirá ejecutándose en segundo plano en la bandeja del sistema (junto al reloj) o en la barra superior de Mac.
   - Marca la casilla **"Arrenca automàticament en encendre l'ordinador"** (*Arrancar automáticamente al encender el ordenador*) si quieres que se inicie solo cada vez que enciendas el equipo.

### 2. Configuración en los navegadores (Chrome, Firefox, Edge, Brave...)

La configuración es **exactamente la misma en todos los navegadores web**, ya que todos utilizan la misma extensión oficial de LanguageTool:

1. Instala la extensión oficial de **LanguageTool** para [Google Chrome](https://chromewebstore.google.com/) o [Mozilla Firefox](https://addons.mozilla.org/) (también funciona en Edge, Brave u Opera).
2. Haz clic en el icono de LanguageTool en la barra del navegador y abre la **Configuración** (icono del engranaje ⚙️).
3. Baja hasta el apartado **Configuración avanzada** (*solo para usuarios profesionales*).
4. En la opción **Servidor de LanguageTool**, selecciona **Servidor local (`localhost`)** (o introduce `http://localhost:8081/v2`).
5. Guarda los cambios. A partir de ahora, las correcciones se realizarán de forma 100 % local y privada en tu ordenador.

### 3. Uso en otras aplicaciones de escritorio

Como el servidor permanece activo en `http://localhost:8081/v2`, también puedes conectarlo con otros programas compatibles:

- **LibreOffice (Writer, Impress, Calc):** Ve a *Herramientas -> Opciones -> Configuración de idioma -> LanguageTool*. Actívalo, escribe `http://localhost:8081/v2` en el campo de URL base (deja usuario y clave API en blanco) y pulsa *Aceptar*.
- **Mozilla Thunderbird:** Instala el complemento oficial *LanguageTool* y selecciona el servidor local en sus opciones.
- **Otros editores:** También es compatible con extensiones para **Obsidian**, **Visual Studio Code** (extensión *LTeX+*) o **TeXstudio**.

### 4. Compilación desde el código fuente (para desarrolladores)

Si deseas compilar los paquetes por tu cuenta, consulta las guías específicas:

- [Instrucciones para Linux](readme-linux.md)
- [Instrucciones para Windows](readme-windows.md)
- [Instrucciones para macOS](readme-mac.md)
