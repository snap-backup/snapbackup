#!/bin/bash
##################
# Snap Backup    #
# Build on macOS #
##################

# Use Homebrew to install the JDK (openjdk) and Ant:
#     url=https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh
#     /bin/bash -c "$(curl -fsSL $url)"
#     brew --version
#     brew install openjdk ant
#     java --version
#     ant -version

banner="Snap Backup - Build"
projectHome=$(cd $(dirname $0)/..; pwd)
iconPng=$projectHome/src/resources/graphics/application/snap-backup-icon.png
attributesFile=$projectHome/src/java/org/snapbackup/settings/SystemAttributes.java
version=$(grep --max-count 1 appVersion $attributesFile | awk -F'"' '{ print $2 }')

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
   which java || exit
   java --version
   javac --version
   which ant || exit
   echo
   }

buildExecutableJar() {
   cd $projectHome/tools
   echo "Ant:"
   ant -version
   ant build
   echo
   }

createResources() {
   cd $projectHome/build
   echo "Create resources:"
   mkdir SnapBackup.iconset
   sips --version
   sips --resampleHeightWidth 480 480 --padToHeightWidth 512 512 $iconPng  \
      --out SnapBackup.iconset/icon_512x512.png
   iconutil --convert icns SnapBackup.iconset
   mkdir -p package/macos
   mv -v SnapBackup.icns package/macos
   sips --resampleHeightWidth 120 120 --padToHeightWidth 175 175 $iconPng  \
      --out package/macos/SnapBackup-background.png
   cp -v package/macos/SnapBackup-background.png package/macos/SnapBackup-background-darkAqua.png
   echo
   }

createMacInstaller() {
   cd $projectHome/build
   echo "Create macOS installer:"
   jpackage --version
   echo "Packaging..."
   jpackage --name SnapBackup --input . --license-file ../LICENSE.txt --main-jar snapbackup.jar  \
      --app-version $version --resource-dir package/macos --type pkg
   mv -v SnapBackup-$version.pkg snap-backup-installer-v$version.pkg
   mv -v snapbackup.jar snapbackup-v$version.jar
   ls -o *.jar *.pkg
   echo
   }

updateReleasesFolder() {
   cd $projectHome
   echo "Releases folder:"
   rm releases/*.jar releases/*.pkg
   cp -v build/snap-backup-installer-*.pkg releases
   cp -v build/snap-backup-installer-*.pkg releases/archive
   cp -v build/snapbackup-*.jar            releases
   cp -v build/snapbackup-*.jar            releases/archive
   echo "[Only add files to the archive folder -- git discard modified files]"
   echo
   }

displayIntro
setupBuildTools
buildExecutableJar
createResources
createMacInstaller
updateReleasesFolder
