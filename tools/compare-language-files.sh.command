#!/bin/sh
###############
# Snap Backup #
###############

projectHome=$(cd $(dirname $0)/..; pwd)

lineCount() {
   cd $projectHome/src/resources/properties
   echo "Properties line count:"
   for file in SnapBackup*.properties; do
      wc -l $file
      done
   echo
   }

nameCheck() {
   cd $projectHome/src/resources/properties
   echo "Properties name check:"
   grep = < SnapBackup.properties | sed s/\=.*// > base-properies.txt
   for file in SnapBackup_*.properties; do
      echo "     $file"
      grep = < $file | sed s/\=.*// | diff - base-properies.txt
      done
   rm base-properies.txt
   echo
   }

echo
echo "Compare Language Files"
echo "======================"
cd $projectHome/src/resources/properties
pwd
echo
lineCount
nameCheck
