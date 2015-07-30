@echo off
::::::::::::::::::::::::::::::::
:: Snap Backup                ::
:: Build on Microsoft Windows ::
::::::::::::::::::::::::::::::::

:: Set JAVA_HOME
for /d %%i in ("\Program Files\Java\jdk*") do set JAVA_HOME=%%i

:: set ANT_HOME
for /d %%i in ("\Apps\Ant\apache-ant*") do set ANT_HOME=%%i

:: Display Variables and launch Ant
set JAVA_HOME
set ANT_HOME
call %ANT_HOME%\bin\ant build

:: Copy to VirtualBox folder
if exist \\VBOXSVR\VirtualBox_Share (
   echo.
   copy ..\..\build\SnapBackupInstaller.exe \\VBOXSVR\VirtualBox_Share
   dir \\VBOXSVR\VirtualBox_Share\SnapBackupInstaller.exe
   )
echo.
pause
