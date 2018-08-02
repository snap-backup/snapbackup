#!/bin/sh
##################
# Snap Backup    #
# snapbackup.org #
##################

# To make this file runnable:
#    $ chmod +x *.sh.command

port=11598  #"sb" -> 115 98
projectHome=$(cd $(dirname $0)/..; pwd)

setupTools() {
   # Check for Node.js installation and download project dependencies
   cd $projectHome
   pwd
   echo
   echo "Node.js:"
   which node || { echo "Need to install Node.js: https://nodejs.org"; exit; }
   node --version
   npm install
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

setupWebServer() {
   cd $projectHome/websites-target
   process=$(pgrep -lf "SimpleHTTPServer $port")
   launch() {
      echo "Launching SimpleHTTPServer:"
      pwd
      python -m SimpleHTTPServer $port &> /dev/null &
      echo
      }
   test -z "$process" && launch
   echo "Web server:"
   pgrep -lf SimpleHTTPServer
   echo
   }

publishWebFiles() {
   cd $projectHome/websites-target
   publishWebRoot=$(grep ^DocumentRoot /private/etc/apache2/httpd.conf | awk -F\" '{ print $2 }')
   publishFolder=$publishWebRoot/centerkey.com
   copyWebFiles() {
      echo "Publishing:"
      echo $publishFolder
      cp -R www.snapbackup.* $publishFolder
      echo
      }
   test -w $publishFolder && copyWebFiles
   }

launchBrowser() {
   url=http://localhost:$port/
   echo "Opening:"
   echo $url
   sleep 2
   open $url
   echo
   }

echo
echo "snapbackup.org"
echo "=============="
setupTools
buildWebFiles
setupWebServer
publishWebFiles
launchBrowser
