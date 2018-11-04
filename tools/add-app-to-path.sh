#!/bin/bash
#
# add-app-to-path.sh
# https://gist.github.com/dpilafian/2ca6c58efa050ae2b2fa1810a6850fc0
# WTFPL
#
# Pass in the name of the app, such as: "ant", "mongo", or "groovy".  If the app is not on the
# path, its "bin" folder will be added to the path.  Additionally, an all uppercase home
# environment variable, such as "JAVA_HOME", will be set to the parent of the "bin" folder.
#
# Assumes folder structure like:
#    ~/apps/groovy/groovy-2.5.2/bin/groovy
#
# Usage:
#     $ source add-app-to-path.sh [APP-NAME]
#
# Example:
#     $ source ~/apps/add-app-to-path.sh groovy

addAppToPath() {
   appsFolder=~/apps
   binFolder=$(find $appsFolder/$appName -name bin -maxdepth 2 | sort | tail -1)
   binFolder=${binFolder:-$(find $appsFolder/$appName -name bin | sort | tail -1)}
   PATH=$PATH:$binFolder
   homeVarName=$(echo $appName | awk '{print toupper($0)}')_HOME
   printf -v $homeVarName $(dirname $binFolder)
   export $homeVarName
   which $appName || { echo "*** Folder 'bin' not found within: $appsFolder/$appName"; exit; }
   }

appName=$1
which $appName || addAppToPath
