#!/bin/sh
###################
##  Snap Backup  ##
###################

# JDK
# ===
# Install the Java SE Devloper Kit for Mac OS X x64:
#    http://www.oracle.com/technetwork/java/javase/downloads/
#
# Ant
# ===
# Install Ant or download the binary distribution (.zip) and unzip into ~/apps/ant
#    Download:  http://ant.apache.org/bindownload.cgi
#    Example install folder:  ~/apps/ant/apache-ant-1.9.6/bin

addAnt() {
   antHome=~/apps/ant/$(ls ~/apps/ant | grep apache-ant | tail -1)
   PATH=$PATH:$antHome/bin
   echo "Path: $PATH"
   }

buildMacInstaller() {
   echo "=== Build native installer (javapackager) ==="
   cd ../build
   pwd
   cp ../src/resources/graphics/application/snap-backup-icon.png .
   mkdir SnapBackup.iconset
   sips -z 128 128 snap-backup-icon.png --out SnapBackup.iconset/icon_128x128.png
   iconutil --convert icns SnapBackup.iconset
   mkdir -p package/macosx
   mv SnapBackup.icns package/macosx
   $JAVA_HOME/bin/javapackager -deploy -native dmg \
      -srcfiles snapbackup.jar -appclass org.snapbackup.Main -name SnapBackup \
      -outdir deploy -outfile SnapBackup -v
   cp deploy/bundles/SnapBackup-1.0.dmg SnapBackupInstaller-v$(cat version.txt).dmg
   }

echo
echo "Snap Backup Build"
echo "================="
cd $(dirname $0)
JAVA_HOME=$(/usr/libexec/java_home)
javac -version
which ant || addAnt
ant -version
ant build
echo
buildMacInstaller
