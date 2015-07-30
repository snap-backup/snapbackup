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

echo
echo "Snap Backup Build"
echo "================="
cd $(dirname $0)
JAVA_HOME=$(/usr/libexec/java_home)
javac -version
which ant || addAnt
ant -version
ant build
