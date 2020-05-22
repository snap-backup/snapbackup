#!/bin/bash
##################
# Snap Backup    #
# snapbackup.org #
##################

# To make this file runnable:
#     $ chmod +x *.sh.command

banner="Snap Backup - Website"
projectHome=$(cd $(dirname $0)/..; pwd)

setupTools() {
   cd $projectHome
   echo
   echo $banner
   echo $(echo $banner | sed s/./=/g)
   pwd
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
   publishWebRoot=$(grep ^DocumentRoot /private/etc/apache2/httpd.conf | awk -F'"' '{ print $2 }')
   publishSite=$publishWebRoot/centerkey.com
   publish() {
      echo "Publishing:"
      echo $publishSite
      cp -R websites-target/www.snapbackup.* $publishSite
      cd $publishSite/www.snapbackup.org
      perfect=$(ls perfect.*); mv -v $perfect perfect3.${perfect##*.}
      echo
      }
   test -w $publishSite && publish
   }

setupWebServer() {
   cd $projectHome
   port=$(grep web-server package.json | sed 's/[^0-9]*\([0-9]*\).*/\1/')  #extract port number from script
   echo "Web Server (indexzero/http-server on node):"
   npm run web-server
   sleep 2  #ensure pid is ready to read
   echo "To stop web server:"
   echo "   $ lsof -P -i :$port"
   echo "   $ kill $(lsof -Pt -i :$port)"
   echo
   }

launchBrowser() {
   url=http://localhost:$port/websites-target/
   echo "Opening:"
   echo $url
   sleep 2
   open $url
   echo
   }

setupTools
buildWebFiles
publishWebFiles
setupWebServer  #port: Snap Backup -> SB -> 83 66 -> 8366
launchBrowser
