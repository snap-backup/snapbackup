; SnapBackup.nsi
;
; This script is perhaps one of the simplest NSIs you can make. All of the
; optional settings are left to their default settings. The installer simply 
; prompts the user asking them where to install, and drops a copy of makensisw.exe
; there.

;--------------------------------
; The name of the installer
Name "Snap Backup"

; The file to write
OutFile "SnapBackupInstaller.exe"


Page license
Page directory
Page components
Page instfiles


LicenseText 'Press "I Agree" only if you accept this license agreement.'
LicenseData SnapBackupLicense.txt

; The default installation directory
InstallDir "$PROGRAMFILES\Center Key Software\Snap Backup"

; The text to prompt the user to enter a directory
DirText "Instalation Folder"

ComponentText "Shortcuts" "" "Select the shortcuts to install:"

;--------------------------------
; The stuff to install
Section "" ;No components page, name is not important

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR
  
  ; Put file there
  File SnapBackup.jar
  File SnapBackupIcon16.ico
  File SnapBackupIcon32.ico
  File Uninstall.txt

MessageBox MB_ICONINFORMATION 'Instalation is complete.  If you do not already have Java installed on your computer, get it at: http://www.java.com'


SectionEnd ; end the section


;--------------------------------
Section "Start Menu Shortcut"
  CreateDirectory "$SMPROGRAMS\Snap Backup"
  CreateShortCut "$SMPROGRAMS\Snap Backup\Snap Backup.lnk" "$INSTDIR\SnapBackup.jar" "" "$INSTDIR\SnapBackupIcon16.ico" 0
  CreateShortCut "$SMPROGRAMS\Snap Backup\Uninstall.lnk" "$INSTDIR\Uninstall.txt"
SectionEnd


;--------------------------------
Section "Desktop Shortcut"
  CreateShortCut "$DESKTOP\Snap Backup.lnk" "$INSTDIR\SnapBackup.jar" "" "$INSTDIR\SnapBackupIcon32.ico" 0
SectionEnd
