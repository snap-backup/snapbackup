#!/bin/bash
###############
# Snap Backup #
###############

banner="Snap Backup - Static Analysis of Java Code"
projectHome=$(cd $(dirname $0)/..; pwd)
pmdVersion=$(curl --silent https://pmd.github.io | grep "Latest Version:" | awk '{ print $3 }')
pmdZipFile=pmd-dist-$pmdVersion-bin.zip
pmdDownload=https://github.com/pmd/pmd/releases/download/pmd_releases%2F$pmdVersion/$pmdZipFile
pmdFolder=$projectHome/tools/static-analysis/pmd/pmd-bin-$pmdVersion

displayIntro() {
   cd $projectHome
   echo
   echo $banner
   echo $(echo $banner | sed s/./=/g)
   pwd
   echo
   }

setupPmd() {
   cd $projectHome
   echo "Setup PMD:"
   echo $pmdVersion
   echo $pmdFolder
   which java || exit
   java --version
   downloadPmd() {
      echo "Downloading..."
      echo $pmdDownload
      mkdir -p tools/static-analysis/pmd
      cd tools/static-analysis/pmd
      pwd
      curl --location --remote-name $pmdDownload
      unzip $pmdZipFile
      ls -o
      rm $pmdZipFile
      }
   test -d $pmdFolder || downloadPmd
   echo
   }

runPmd() {
   cd $projectHome/tools/static-analysis
   echo "Run PMD:"
   pwd
   report=$projectHome/tools/static-analysis/report.html
   $pmdFolder/bin/pmd check --dir $projectHome/src/java --rulesets rule-set-good-java.xml \
      --no-cache --format html --report-file $report
   fixFont='s|<head>|<head><style>html { font-family: system-ui; }</style>|'
   sed -i "" "$fixFont" $report
   fixPath='s|[^>]*\/\(snapbackup\/src\/java\/\)|\1|'
   sed -i "" "$fixPath" $report
   echo
   echo "Report:"
   echo $report
   echo
   }

displayIntro
setupPmd
runPmd
sleep 2
open $report
