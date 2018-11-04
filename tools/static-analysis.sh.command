#!/bin/bash
###############
# Snap Backup #
###############

banner="Snap Backup - Static Analysis of Java Code"
pmdVersion=6.9.0
projectHome=$(cd $(dirname $0)/..; pwd)

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
   source tools/add-app-to-path.sh java
   pmdFolder=$projectHome/tools/static-analysis/pmd/pmd-bin-$pmdVersion
   echo $pmdFolder
   downloadPmd() {
      echo "Downloading..."
      mkdir -p tools/static-analysis/pmd
      cd tools/static-analysis/pmd
      pwd
      curl --location --remote-name https://github.com/pmd/pmd/releases/download/pmd_releases%2F$pmdVersion/pmd-bin-$pmdVersion.zip
      unzip pmd-bin-$pmdVersion.zip
      ls -o
      rm pmd-bin-$pmdVersion.zip
      }
   test -d $pmdFolder || downloadPmd
   echo
   }

runPmd() {
   cd $projectHome/tools/static-analysis
   echo "Run PMD:"
   pwd
   report=$projectHome/tools/static-analysis/report.html
   $pmdFolder/bin/run.sh pmd -dir $projectHome/src/java -rulesets ruleset-good-java.xml -f html > $report
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
