#!/bin/sh

###########################
#  Snap Backup            #
#  http://snapbackup.org  #
###########################

webServerFolder=~/Dropbox/Documents/Sites/snapbackup.org
webServerUrl=http://localhost/snapbackup.org

echo
echo "Snap Backup Website"
echo "###################"
cd $(dirname $0)
pwd

# Build HTML files (run DSI templating)
cp screen-index.bhtml screen-@mac.bhtml
cp screen-index.bhtml screen-@ubuntu.bhtml
cp screen-index.bhtml screen-@win.bhtml
cp screen-index.bhtml screen-@vista.bhtml
cp screen-index.bhtml screen-@metal.bhtml
[ ! -f dsi.jar ] && curl --remote-name http://centerkey.com/dsi/download/dsi.jar
java -jar dsi.jar
rm screen-@*.bhtml
echo

# Put web files into "httpdocs" folder
targetFolder=../httpdocs
rm -rf $targetFolder
mkdir $targetFolder
target=$(cd $targetFolder; pwd)
echo "Target:"
echo $target
mv -v *.html $target
cd ..
cp -v *.css *.js $target
cp -R graphics app $target
cd $target
for file in *-index.html; do
   folder=$(echo $file | sed "s/-.*//")
   mkdir $folder
   mv -v $file $folder/index.html
   done
for file in screen-@*.html; do
   os=$(echo $file | sed "s/screen-@//" | sed "s/.html//")
   mv -v $file screen/$os.html
   done
mv -v feedback-thanks.html   feedback/thanks.html
mv -v translate-preview.html translate/preview.html
mv -v graphics/bookmark.ico  favicon.ico
echo

# List files
echo "Website:"
cd $target
pwd
url="$target/index.html"
updateWebServer() {
   echo $webServerFolder
   cp -R * $webServerFolder
   url=$webServerUrl
   }
[ -d $webServerFolder ] && updateWebServer
echo "Opening -> $url"
open $url
