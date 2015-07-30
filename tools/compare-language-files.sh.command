#!/bin/sh
###################
##  Snap Backup  ##
###################

lineCount() {
   echo "\nProperties line count:"
   for file in SnapBackup*.properties; do
      wc -l $file
      done
   }

nameCheck() {
   echo "\nProperties name check:"
   grep = < SnapBackup.properties | sed s/\=.*// > base-properies.txt
   for file in SnapBackup_*.properties; do
      echo "     $file"
      grep = < $file | sed s/\=.*//	| diff - base-properies.txt
      done
   rm base-properies.txt
   }

echo
echo "Compare Language Files"
echo "======================"
cd $(dirname $0)
cd ../src/resources/properties
pwd
lineCount
nameCheck
echo
