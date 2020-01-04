#!/bin/bash
##################
# Snap Backup    #
# Build on macOS #
##################

# JDK
# ===
# Install the Java SE Development Kit (JDK) for macOS:
#    https://www.oracle.com/technetwork/java/javase/downloads
#    Note: OpenJDK does not contain javapackager needed to create the installer
#
# Ant
# ===
# Install Ant or download and unzip into the "~/apps/ant" folder:
#    Download --> https://ant.apache.org/bindownload.cgi (".zip archive")
#    Example install folder --> ~/apps/ant/apache-ant-1.10.5/bin

banner="Snap Backup - Build"
projectHome=$(cd $(dirname $0)/..; pwd)

displayIntro() {
   cd $projectHome
   echo
   echo $banner
   echo $(echo $banner | sed s/./=/g)
   pwd
   echo
   }

setupBuildTools() {
   cd $projectHome
   echo "Java:"
   echo $JAVA_HOME
   which java || exit
   java -version
   javac -version
   echo
   echo "Ant:"
   which ant || exit
   ant -version
   attributesFile=src/java/org/snapbackup/settings/SystemAttributes.java
   version=$(grep --max-count 1 appVersion $attributesFile | awk -F'"' '{ print $2 }')
   echo
   }

buildExecutableJar() {
   cd $projectHome/tools
   ant build
   echo
   }

buildMacInstaller() {
   cd $projectHome
   echo "Applications icons:"
   cp src/resources/graphics/application/snap-backup-icon.png build
   cd build
   mkdir SnapBackup.iconset
   sips -z   16   16 snap-backup-icon.png --out SnapBackup.iconset/icon_16x16.png
   sips -z   32   32 snap-backup-icon.png --out SnapBackup.iconset/icon_16x16@2x.png
   sips -z   32   32 snap-backup-icon.png --out SnapBackup.iconset/icon_32x32.png
   sips -z  128  128 snap-backup-icon.png --out SnapBackup.iconset/icon_32x32@2x.png
   sips -z  128  128 snap-backup-icon.png --out SnapBackup.iconset/icon_128x128.png
   sips -z  256  256 snap-backup-icon.png --out SnapBackup.iconset/icon_128x128@2x.png
   sips -z  256  256 snap-backup-icon.png --out SnapBackup.iconset/icon_256x256.png
   sips -z  512  512 snap-backup-icon.png --out SnapBackup.iconset/icon_256x256@2x.png
   sips -z  512  512 snap-backup-icon.png --out SnapBackup.iconset/icon_512x512.png
   sips -z 1024 1024 snap-backup-icon.png --out SnapBackup.iconset/icon_512x512@2x.png
   iconutil --convert icns SnapBackup.iconset
   mkdir -p package/macosx
   sips -z 100 100 -p 150 150 snap-backup-icon.png --out package/macosx/SnapBackup-background.png
   mv SnapBackup.icns package/macosx
   echo "macOS installer (javapackager):"
   $JAVA_HOME/bin/javapackager -version
   $JAVA_HOME/bin/javapackager -deploy -native pkg -name SnapBackup \
      -BappVersion=$version -Bicon=package/macosx/SnapBackup.icns \
      -srcdir . -srcfiles snapbackup.jar -appclass org.snapbackup.SnapBackup \
      -outdir out -v
   cp out/SnapBackup-*.pkg snap-backup-installer-v$version.pkg
   mv snapbackup.jar snapbackup-v$version.jar
   echo "Output:"
   ls -o *.pkg
   echo
   }

updateReleasesFolder() {
   cd $projectHome
   echo "Releases folder:"
   cp -v build/snap-backup-installer-*.pkg releases
   cp -v build/snap-backup-installer-*.pkg releases/archive
   cp -v build/snapbackup-*.jar            releases
   cp -v build/snapbackup-*.jar            releases/archive
   echo
   }

displayIntro
setupBuildTools
buildExecutableJar
buildMacInstaller
updateReleasesFolder
