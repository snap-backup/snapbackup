////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// SystemAttributes.java                                                      //
//                                                                            //
// System Attributes:                                                         //
//    This object is for system level settings such as version numbers, list  //
//    of languages, constants, and other system attributes.                   //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.settings;

import java.util.Locale;
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;

// Constant values that do not belong in the properties file.
public class SystemAttributes {

   // Release constants
   public static final String appTitle =   "Snap Backup";
   public static final String appName =    "SnapBackup";  //".properties" name
   public static final String appVersion = "6.7.0";  //keep in sync with package.json
   // 6.7.0 - 2024/12/11 - Update Italian translation
   // 6.6.0 - 2024/02/04 - Support Apple silicon (M1, M2, and M3)
   // 6.5.0 - 2022/09/30 - Auto save settings and new --list and --current CLI flags
   // 6.4.1 - 2022/08/28 - Filter out files stored in remote clouds
   // 6.4.0 - 2022/02/15 - Support openjdk and use jpackage to create installer
   // 6.3.0 - 2018/08/03 - Support Java 9
   // 6.2.0 - 2017/07/30 - Exclude Windows recycle bin and improved showing largest files option
   // 6.1.0 - 2016/10/16 - Automatically skip extraneous files and switch to semantic versioning
   // 6.0 -   2015/08/16 - Upgrade to Java 7
   // 5.6 -   2011/04/23 - Albanian
   // 5.5 -   2011/01/02 - Romanian
   // 5.4 -   2010/10/24 - Manifest splash screen and convert to .org
   // 5.3 -   2010/04/17 - Indonesian
   // 5.2 -   2010/01/02 - Added reporting of largest files to the Message Log
   // 5.1 -   2009/06/02 - Added Check for Updates feature (and moved prefs)
   // 5.0 -   2009/05/06 - Added import/export and switched to Java 5.0
   // 4.5 -   2008/09/07 - Slovene (Slovenian)
   // 4.4 -   2006/06/09 - Korean, user guide print button, and exclude .DS_Store on Macs
   // 4.3 -   2006/05/07 - Portuguese
   // 4.2 -   2006/04/22 - Spanish
   // 4.1 -   2006/01/18 - Dutch
   // 4.0 -   2005/12/18 - Configurable source and log height (options)
   // 3.8 -   2005/12/01 - Skins (save user selection in preferences)
   // 3.7 -   2005/10/23 - Russian
   // 3.6 -   2005/09/22 - Esperanto and command-line
   // 3.5 -   2005/09/?? - Options feature
   // 3.4 -   2005/09/02 - Folder support for filters
   // 3.3 -   2005/07/05 - German
   // 3.2 -   2005/06/17 - Italian
   // 3.1 -   2005/06/01 - Filters
   // 3.0 -   2005/05/09 - Multiple profiles
   public static final String[] localeCodes = {
      "en", "eo", "es", "de", "fr", "id", "it", "ko", "nl", "pt", "ro", "ru", "sl", "sq"
      };
      // Codes -- https://www.loc.gov/standards/iso639-2/php/code_list.php
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
      { "WVam",                   "https://github.com/WVam" },                            //"it"
      };
   public static final String appAuthors = "Pilaf T. Pilafian";  //names of code contributors
   public static final String appCopyright =
      "Copyright &copy; Individual contributors to the Snap Backup project<br>" +
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
   public static final String replacementChar = "%";
   public static final String startHtml =       "<html>";
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
   public static final String errMsgHeader =          "ERROR -- '";
   public static final String errMsgMissingResource = "' property not found in: ";
   public static final String prefLocale =            "locale";
   public static final String prefChar =              ".";
   public static final String prefPrefix =            appName.toLowerCase(Locale.ROOT) + prefChar;
   public static final String prefProfilePrefix =     prefPrefix + "~";
   public static final String prefProfilePostfix =    "~" + prefChar;
   public static final String splitStr =              "~@~";  //delimiter for multi-line data

   // System information retrieved from JVM
   static final Properties     sysInfo =       System.getProperties();
   public static final String  userName =      sysInfo.getProperty("user.name");
   public static final String  userHomeDir =   sysInfo.getProperty("user.home");
   public static final String  fileSeparator = sysInfo.getProperty("file.separator");
   public static final boolean isMac =         sysInfo.getProperty("mrj.version") != null;
   public static final boolean evilWinSys =    fileSeparator.equals("\\");
   public static final String  evilWinDrive =  userHomeDir.indexOf(":\\") == 1 ?
      userHomeDir.substring(0, 2) : "";  //example: "C:\zzz" --> "C:"
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
