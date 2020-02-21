////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// SystemAttributes.java                                                      //
//                                                                            //
// System Attributes:                                                         //
//    This object is for system level settings such as version numbers, list  //
//    of languages, constants, and other system attributes.                   //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.settings;

import java.util.Locale;
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;

//Constant values that do not belong in the properties file.
public class SystemAttributes {

   //Release constants
   public static final String appName = "SnapBackup";  //".properties" name
   public static final String appVersion = "6.3.0";  //keep in sync with package.json
   // 6.3.0 - 8/3/2018 -   Support Java 9
   // 6.2.0 - 7/30/2017 -  Exclude Windows recycle bin and improved showing largest files option
   // 6.1.0 - 10/16/2016 - Automatically skip extraneous files and switch to semantic versioning
   // 6.0 -   8/16/2015 -  Upgrade to Java 7
   // 5.6 -   4/23/2011 -  Albanian
   // 5.5 -   1/2/2011 -   Romanian
   // 5.4 -   10/24/2010 - Manifest splash screen and convert to .org
   // 5.3 -   4/17/2010 -  Indonesian
   // 5.2 -   1/2/2010 -   Added reporting of largest files to the Message Log
   // 5.1 -   6/2/2009 -   Added Check for Updates feature (and moved prefs)
   // 5.0 -   5/6/2009 -   Added import/export and switched to Java 5.0
   // 4.5 -   9/7/2008 -   Slovene (Slovenian)
   // 4.4 -   6/9/2006 -   Korean, user guide print button, and exclude .DS_Store on Macs
   // 4.3 -   5/7/2006 -   Portuguese
   // 4.2 -   4/22/2006 -  Spanish
   // 4.1 -   1/18/2006 -  Dutch
   // 4.0 -   12/18/2005 - Configurable source and log height (options)
   // 3.8 -   12/1/2005 -  Skins (save user selection in preferences)
   // 3.7 -   10/23/2005 - Russian
   // 3.6 -   9/22/2005 -  Esperanto and command-line
   // 3.5 -   9/??/2005 -  Options feature
   // 3.4 -   9/2/2005 -   Folder support for filters
   // 3.3 -   7/5/2005 -   German
   // 3.2 -   6/17/2005 -  Italian
   // 3.1 -   6/1/2005 -   Filters
   // 3.0 -   5/9/2005 -   Multiple profiles
   public static final String[] localeCodes = {
      "en", "eo", "es", "de", "fr", "id", "it", "ko", "nl", "pt", "ro", "ru", "sl", "sq"
      };
      // Codes -- https://www.loc.gov/standards/iso639-2/php/code_list.php
      // Flags -- https://www.paypal.com/be/webapps/mpp/country-worldwide
      // See build.xml for workaround to "in" bug regarding Indonesian:
      //    https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6457127
   public static final String[][] appTranslators = {
      { "Giorgio Ponza",          "https://en.wikipedia.org/wiki/Italian_language" },     //"it"
      { "Pasc&aacute;l Bihler",   "http://www.bi-on.de" },                                //"de"
      { "Carlos Maltzahn",        "https://users.soe.ucsc.edu/~carlosm" },                //"de"
      { "Suzanne Bolduc",         "http://esperanto.net" },                               //"eo"
      { "Elena Kogan",            "https://en.wikipedia.org/wiki/Russian_language" },     //"ru"
      { "Oscar Laurens Schrover", "https://en.wikipedia.org/wiki/Dutch_language" },       //"nl"
      { "Angel Herr&aacute;ez",   "http://biomodel.uah.es" },                             //"es"
      { "Antonio de Rezende Jr.", "https://en.wikipedia.org/wiki/Portuguese_language" },  //"pt"
      { "Sa&#353;o Topolovec",    "https://en.wikipedia.org/wiki/Slovene_language" },     //"sl" [&scaron;]
      { "Andy Saksono",           "https://en.wikipedia.org/wiki/Indonesian_language" },  //"id"
      { "Dany",                   "https://en.wikipedia.org/wiki/Romanian_language" },    //"ro"
      { "Vahidin Qerimi",         "http://vahidin.wordpress.com" },                       //"sq"
      };
   public static final String appAuthors = "Dem Pilafian";  //Names of code contributors
   public static final String appCopyright =
      "Copyright &copy; individual contributors to the Snap Backup project<br>" +
      "Snap Backup&reg; is a registered trademark of Center Key";

   // Useful constants
   public static final String nullStr =         "";
   public static final String space =           " ";
   public static final String comma =           ",";
   public static final String atSign =          "@";
   public static final String dataPrompt =      ": ";
   public static final String tab =             "     ";
   public static final String dividerStr =      " / ";
   public static final String newLine =         "\n";
   public static final String startHtml =       "<html lang=en>";
   public static final String endHtml =         "</html>";
   public static final String headerPre =       startHtml + "<center><br><b><font size=+2 color=navy face='comic sans ms, sand, fantasy'>";
   public static final String headerMid =       "</font></b>";
   public static final String headerSplashTag = "<br><br><b><font color=gray>Center Key</font></b>";
   public static final String headerPost =      "<br>&nbsp;</center>" + endHtml;
   public static final String homeURL =         "https://snapbackup.org";  //on About Box
   public static final String updatesURL =      homeURL + "/version/";  //not displayed
   public static final String downloadURL =     homeURL + "/download";  //on About Box
   public static final String visitURL =        homeURL + "/app?v=" + appVersion;  //not displayed

   // Bootstrap data needed before reading properties file
   public static final String errMsgHeader = "ERROR -- '";
   public static final String errMsgMissingResource = "' property not found in: ";
   public static final String prefLocale = "locale";
   public static final String prefChar = ".";
   public static final String prefPrefix = appName.toLowerCase() + prefChar;
   public static final String prefProfilePrefix = prefPrefix + "~";
   public static final String prefProfilePostfix = "~" + prefChar;
   public static final String splitStr = "~@~";  //delimiter for multi-line data
   public static final String cmdLineDefaultProfile = "~";

   // System information retrieved from JVM
   static final Properties     sysInfo =       System.getProperties();
   public static final String  userName =      sysInfo.getProperty("user.name");
   public static final String  userHomeDir =   sysInfo.getProperty("user.home");
   public static final String  fileSeparator = sysInfo.getProperty("file.separator");
   public static final boolean isMac =         sysInfo.getProperty("mrj.version") != null;
   public static final boolean evilWinSys =    fileSeparator.equals("\\");
   public static final String  evilWinDrive =  (userHomeDir.indexOf(":\\") == 1 ?
      userHomeDir.substring(0, 2) : "");  //example: "C:\zzz" --> "C:"
   public static final String osInfo =  //example: "Mac OS X (10.12/x86_64/en)"
      sysInfo.getProperty("os.name") + " (" +
      sysInfo.getProperty("os.version") + fileSeparator +
      sysInfo.getProperty("os.arch") + fileSeparator +
      Locale.getDefault().getLanguage() + ")";
   public static final String javaVersion =  //example: "Java 1.8.0_102, Oracle Corporation"
      "Java " + sysInfo.getProperty("java.version") + ", " +
      sysInfo.getProperty("java.vendor");
   public static final String javaHomeDir = sysInfo.getProperty("java.home");
   public static final String javaVMInfo =
      // Example: "Java HotSpot(TM) 64-Bit Server VM, 25.102-b14 [1820 MB]"
      sysInfo.getProperty("java.vm.name") + ", " +
      sysInfo.getProperty("java.vm.version") + " [" +
      Runtime.getRuntime().maxMemory()/(1024*1024) + " MB]";
   static final String docsExtra =  //macOS localization changes display name
      isMac ? fileSeparator + "Documents" : "";
   static final String desktopExtra =  //macOS localization changes display name
      isMac ? fileSeparator + "Desktop" : "";
   static final FileSystemView fileSysView = FileSystemView.getFileSystemView();
   public static final String userDocsDir =
      fileSysView.getDefaultDirectory().getAbsolutePath() + docsExtra;
   public static final String userDesktopDir =
      fileSysView.getHomeDirectory().getAbsolutePath() + desktopExtra;

}
