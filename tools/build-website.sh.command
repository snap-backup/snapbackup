#!/bin/bash
##################
# Snap Backup    #
# snapbackup.org #
##################

# To make this file runnable:
#     $ chmod +x *.sh.command

banner="Snap Backup - Website"
projectHome=$(cd $(dirname $0)/..; pwd)
apacheCfg=/usr/local/etc/httpd
apacheLog=/usr/local/var/log/httpd/error_log
webDocRoot=$(grep ^DocumentRoot $apacheCfg/httpd.conf | awk -F'"' '{ print $2 }')

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
   npm update
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
   publish() {
      echo "Publishing:"
      echo $publishSite
      cp -R websites-target/www.snapbackup.* $publishSite
      echo
      }
   test -w $publishSite && publish
   }

launchBrowser() {
   cd $projectHome
   npm run interactive
   }

setupTools
buildWebFiles
publishWebFiles
launchBrowser
