#!/bin/bash
##################
# Snap Backup    #
# snapbackup.org #
##################

# To make this file runnable:
#     $ chmod +x *.sh.command

banner="Snap Backup - Website"
projectHome=$(cd $(dirname $0)/..; pwd)
pkgInstallHome=$(dirname $(dirname $(which httpd)))
apacheCfg=$pkgInstallHome/etc/httpd
apacheLog=$pkgInstallHome/var/log/httpd/error_log
webDocRoot=$(grep ^DocumentRoot $apacheCfg/httpd.conf | awk -F'"' '{ print $2 }')
cliFlagMsg="Use the '--no-server' flag to skip the interactive web server."
cliFlag=$1

setupTools() {
   cd $projectHome
   echo
   echo $banner
   echo $(echo $banner | sed s/./=/g)
   pwd
   test -d .git || { echo "Project must be in a git repository."; exit; }
   git restore dist/* &>/dev/null
   git pull --ff-only
   echo
   echo "Node.js:"
   which node || { echo "Need to install Node.js: https://nodejs.org"; exit; }
   node --version
   npm install --no-fund
   npm update --no-fund
   npm outdated
   echo
   }

buildWebFiles() {
   cd $projectHome
   attributesFile=src/java/org/snapbackup/settings/SystemAttributes.java
   versionJava=$(grep --max-count 1 appVersion $attributesFile | awk -F'"' '{ print $2 }')
   versionHtml=$(grep --max-count 1 version package.json | awk -F'"' '{print $4}')
   warning="v$versionJava (SystemAttributes.java) does not match v$versionHtml (package.json)"
   test $versionJava != $versionHtml && echo "*** WARNING: $warning"
   echo "Build:"
   echo "v$versionHtml"
   npm test
   echo
   }

publishWebFiles() {
   cd $projectHome
   publishSite=$webDocRoot/centerkey.com
   publishFolder=$publishSite/www.snapbackup.org
   publish() {
      echo "Publishing:"
      echo $publishSite
      mkdir -pv $publishFolder
      cp -R website-target/3-rev/* $publishFolder
      ls -o $publishSite | grep snapbackup
      test -x "$(which tree)" && tree $publishFolder
      }
   test -w $publishSite && publish
   }

interactiveServer() {
   cd $projectHome
   test "$cliFlag" = "--no-server" && echo "Skipping interactive server (--no-server)." || echo $cliFlagMsg
   test "$cliFlag" != "--no-server" && npm run interactive
   echo
   }

setupTools
buildWebFiles
publishWebFiles
interactiveServer
