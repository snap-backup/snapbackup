#!/bin/sh
#########################
##  Snap Backup        ##
##  Build on macOS  ##
#########################

# JDK
# ===
# Install the Java SE Devloper Kit for macOS x64:
#    http://www.oracle.com/technetwork/java/javase/downloads
#
# Ant
# ===
# Install Ant or download and unzip into the "~/apps/ant" folder:
#    Download --> http://ant.apache.org/bindownload.cgi (".zip archive")
#    Example install folder --> ~/apps/ant/apache-ant-1.9.7/bin

projectHome=$(cd $(dirname $0)/..; pwd)

addAnt() {
   antHome=~/apps/ant/$(ls ~/apps/ant | grep apache-ant | tail -1)
   PATH=$PATH:$antHome/bin
   echo "Path: $PATH"
   which ant || echo "*** Must install ant first. See: build.sh.command"
   echo
   }

setup() {
   cd $projectHome
   JAVA_HOME=$(/usr/libexec/java_home)
   javac -version
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
   cd $projectHome/build
   $JAVA_HOME/bin/javapackager -version
   cp ../src/resources/graphics/application/snap-backup-icon.png .
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
   $JAVA_HOME/bin/javapackager -deploy -native dmg \
      -srcfiles snapbackup.jar -appclass org.snapbackup.Main \
      -name SnapBackup -vendor "Snap Backup" -outdir deploy -outfile SnapBackup -v
   cp deploy/bundles/SnapBackup-1.0.dmg snap-backup-installer-$version.dmg
   cp snapbackup.jar snapbackup-$version.jar
   cp -v snapbackup*.jar snap-backup-installer-*.dmg ../releases
   pwd
   ls -l *.dmg
   echo
   }

echo
echo "Snap Backup Build"
echo "================="
setup
buildExecutableJar
buildMacInstaller
