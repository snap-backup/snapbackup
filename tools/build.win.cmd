@echo off
::::::::::::::::::::::::::::::::
:: Snap Backup                ::
:: Build on Microsoft Windows ::
::::::::::::::::::::::::::::::::

:: Script tested on:
::    Windows 10

:: JDK
:: ===
:: Download and install OpenJDK for "Windows x64 msi":
::    https://docs.microsoft.com/en-us/java/openjdk/download
::
:: Ant
:: ===
:: Download and unzip Ant into the "\Apps\Ant" folder:
::    Download --> https://ant.apache.org/bindownload.cgi  (use latest ".zip archive")
::    Example folder --> \Apps\Ant\apache-ant-1.10.12\bin
::
:: ImageMagick
:: ===========
:: Download ImageMagick and install with the default settings:
::    https://imagemagick.org/script/download.php#windows  (use first download listed)
::
:: WiX Toolset
:: ===========
:: Enable .NET Framework:
::    Start --> Windows System (right column) --> Control Panel --> Programs -->
::    Turn Windows features on or off --> check ".NET Framework 3.5" --> OK -->
::    Let Windows Update download the files for you  -->  Close
:: Download and run the latest "WiX Toolset build tools" installer:
::    https://github.com/wixtoolset/wix3/releases  (example: wix311.exe)

::::::::::::::::::::::::::::::::
echo Snap Backup Build
echo =================
cd
java --version
echo.

::::::::::::::::::::::::::::::::
echo Compile
echo -------
for /d %%i in ("\Apps\Ant\apache-ant-*") do set antHome=%%i
echo %antHome%
call "%antHome%\bin\ant" -version
call "%antHome%\bin\ant" build
if not exist "%antHome%\bin\ant" pause & exit
echo.

::::::::::::::::::::::::::::::::
echo Resources
echo ---------
cd ..\build
cd
mkdir package\windows
magick --version
magick convert ..\src\resources\graphics\application\snap-backup-icon.png ^
   package\windows\SnapBackup.ico
dir package\windows
echo.

::::::::::::::::::::::::::::::::
echo Installer
echo ---------
jpackage --version
set /p version=<version.txt
:: TODO: Replace version with ->
::    attributesFile=src/java/org/snapbackup/settings/SystemAttributes.java
::    version=$(grep --max-count 1 appVersion $attributesFile | awk -F'"' '{ print $2 }')
::    for /f "tokens=3" %%v in (findstr "appVersion =" ?attributesFile?) do version=%%v
echo Packaging v%version%...
jpackage --name SnapBackup --input . --license-file ../LICENSE.txt ^
   --main-jar snapbackup.jar --app-version %version% ^
   --icon package\windows\SnapBackup.ico --win-shortcut ^
   --win-menu --win-menu-group "Snap Backup" ^
   --resource-dir package/windows --type msi
move SnapBackup-%version%.msi snap-backup-installer-v%version%.msi
cd ..
del releases\*.msi
copy build\snap-backup-installer-v%version%.msi releases\snap-backup-installer-v%version%.msi
copy build\snap-backup-installer-v%version%.msi releases\archive\snap-backup-installer-v%version%.msi
dir releases
echo.
echo To launch:
echo    java -jar %CD%\releases\snapbackup-v%version%.jar
echo.

::::::::::::::::::::::::::::::::
echo Done.
pause
