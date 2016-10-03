#!/bin/sh
#########################
##  Snap Backup        ##
##  Build on Mac OS X  ##
#########################

# JDK
# ===
# Install the Java SE Devloper Kit for Mac OS X x64:
#    http://www.oracle.com/technetwork/java/javase/downloads
#
# Ant
# ===
# Install Ant or download and unzip into the "~/apps/ant" folder:
#    Download --> http://ant.apache.org/bindownload.cgi (".zip archive")
#    Example install folder --> ~/apps/ant/apache-ant-1.9.7/bin

addAnt() {
   antHome=~/apps/ant/$(ls ~/apps/ant | grep apache-ant | tail -1)
   PATH=$PATH:$antHome/bin
   echo "Path: $PATH"
   which ant || echo "*** Must install ant first. See: build.sh.command"
   }

buildMacInstaller() {
   echo
   $JAVA_HOME/bin/javapackager -version
   cd ../build
   cp ../src/resources/graphics/application/snap-backup-icon.png .
   mkdir SnapBackup.iconset
   sips -z 128 128 snap-backup-icon.png --out SnapBackup.iconset/icon_128x128.png
   iconutil --convert icns SnapBackup.iconset
   mkdir -p package/macosx
   mv SnapBackup.icns package/macosx
   $JAVA_HOME/bin/javapackager -deploy -native dmg \
      -srcfiles snapbackup.jar -appclass org.snapbackup.Main \
      -name SnapBackup -vendor "Snap Backup" -outdir deploy -outfile SnapBackup -v
   cp deploy/bundles/SnapBackup-1.0.dmg snap-backup-installer-v$(cat version.txt).dmg
   pwd
   ls -l *.dmg
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
buildMacInstaller
