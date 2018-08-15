#!/bin/sh
####################
## Snap Backup    ##
## Build on macOS ##
####################

# JDK
# ===
# Install the Java SE Devloper Kit for macOS x64:
#    https://www.oracle.com/technetwork/java/javase/downloads
#
# Ant
# ===
# Install Ant or download and unzip into the "~/apps/ant" folder:
#    Download --> https://ant.apache.org/bindownload.cgi (".zip archive")
#    Example install folder --> ~/apps/ant/apache-ant-1.9.7/bin

banner="Snap Backup - Build"
projectHome=$(cd $(dirname $0)/..; pwd)

displayIntro() {
   cd $projectHome
   echo
   echo $banner
   echo $(echo $banner | sed -e "s/./=/g")
   pwd
   echo
   }

setupBuildTools() {
   cd $projectHome
   JAVA_HOME=$(/usr/libexec/java_home)
   echo "Java:"
   echo $JAVA_HOME
   java -version
   javac -version
   echo
   echo "Ant:"
   addAnt() {
      antHome=~/apps/ant/$(ls ~/apps/ant | grep apache-ant | tail -1)
      PATH=$PATH:$antHome/bin
      which ant || echo "*** Must install ant first. See: build.sh.command"
      echo
      }
   which ant || addAnt
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
   mv SnapBackup.icns package/macosx
   echo "macOS installer (javapackager):"
   $JAVA_HOME/bin/javapackager -version
   $JAVA_HOME/bin/javapackager -deploy -native pkg -name SnapBackup \
      -BappVersion=$version -Bicon=package/macosx/SnapBackup.icns \
      -srcdir . -srcfiles snapbackup.jar -appclass org.snapbackup.SnapBackup \
      -outdir out -v
   cp out/SnapBackup-*.pkg snap-backup-installer-$version.pkg
   mv snapbackup.jar snapbackup-$version.jar
   echo "Output:"
   ls -l *.pkg
   echo
   }

updateReleasesFolder() {
   cd $projectHome
   echo "Releases folder:"
   cp -v build/snap-backup-installer-*.pkg releases
   cp -v build/snapbackup-*.jar            releases
   cp -v build/snapbackup-*.jar            releases/snapbackup-latest.jar
   echo
   }

displayIntro
setupBuildTools
buildExecutableJar
buildMacInstaller
updateReleasesFolder
