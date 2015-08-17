@echo off
::::::::::::::::::::::::::::::::::::
::  Snap Backup                   ::
::  Build on Microsoft Windows XP ::
::::::::::::::::::::::::::::::::::::

:: JDK
:: ===
:: Install the Java SE Devloper Kit:
::    http://www.oracle.com/technetwork/java/javase/downloads ("Windows x86")
::
:: Ant
:: ===
:: Download and unzip Ant into the "\Apps\Ant" folder:
::    Download --> http://ant.apache.org/bindownload.cgi (".zip archive")
::    Example install folder --> \Apps\Ant\apache-ant-1.9.6\bin
::
:: ImageMagick
:: ===========
:: Download and install ImageMagick with the default settings:
::    http://www.imagemagick.org/script/binary-releases.php ("Win32 dynamic at 16 bits-per-pixel")
::
:: WiX Toolset
:: ===========
:: Download and install the WiX toolset with the default settings:
::   http://wixtoolset.org ("RECOMMENDED DOWNLOAD")

:: Set JAVA_HOME
for /d %%i in ("\Program Files\Java\jdk*") do set JAVA_HOME=%%i

:: Set ANT_HOME
for /d %%i in ("\Apps\Ant\apache-ant*") do set ANT_HOME=%%i

:: Display variables and launch Ant
set JAVA_HOME
set ANT_HOME
call "%JAVA_HOME%\bin\javac" -version
call "%ANT_HOME%\bin\ant" -version
call "%ANT_HOME%\bin\ant" build
echo.

:: Create native installer (javapackager)
call "%JAVA_HOME%\bin\javapackager" -version
cd ..\build
copy ..\src\resources\graphics\application\snap-backup-icon.png .
convert snap-backup-icon.png SnapBackup.ico
mkdir package\windows
move SnapBackup.ico package\windows
call "%JAVA_HOME%\bin\javapackager" -deploy -native msi ^
   -srcfiles snapbackup.jar -appclass org.snapbackup.Main ^
   -name SnapBackup -vendor "Snap Backup" -outdir deploy -outfile SnapBackup -v
set /p version=<version.txt
copy deploy\bundles\SnapBackup-1.0.msi snap-backup-installer-v%version%.msi
echo.

:: Copy to VirtualBox folder
if exist \\VBOXSVR\VirtualBox_Share (
   copy *.msi          \\VBOXSVR\VirtualBox_Share
   copy ..\tools\*.cmd \\VBOXSVR\VirtualBox_Share
   dir                 \\VBOXSVR\VirtualBox_Share
   echo.
   )

pause
