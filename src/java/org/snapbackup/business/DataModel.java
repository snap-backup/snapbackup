////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// DataModel.java                                                             //
//                                                                            //
// Data Model:                                                                //
//    This object is the business logic of MVC.                               //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.business;

import java.text.NumberFormat;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;
import org.snapbackup.logger.Logger;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;
import org.snapbackup.ui.SnapBackupFrame;
import org.snapbackup.ui.UIProperties;
import org.snapbackup.ui.backupprogress.BackupProgressDialog;
import org.snapbackup.ui.options.Options;
import org.snapbackup.utilities.DateStamp;
import org.snapbackup.utilities.FileSys;
import org.snapbackup.utilities.Str;
import org.snapbackup.utilities.ZipEngine;

public abstract class DataModel {

   // Top-lever user prefereences
   static final String prefAppVersion =            "AppVersion";
   static final String prefShowLanguages =         "ShowLanguages";
   static final String prefFiltersEnabled =        "FiltersEnabled";
   static final String prefShowProfiles =          "ShowProfiles";
   public static final String prefCurrentProfile = "CurrentProfile";

   // User preferences associated with each profile
   static final String prefProfileName =              "ProfileName";
   static final String prefSrcDataList =              "SrcDataList";
   static final String prefSrcDataIncludeList =       "SrcDataIncludeList";        //filter
   static final String prefSrcDataExcludeList =       "SrcDataExcludeList";        //filter
   static final String prefSrcDataExcludeFolderList = "SrcDataExcludeFolderList";  //filter
   static final String prefSrcDataExcludeSizeList =   "SrcDataExcludeSizeList";    //filter
   static final String prefBackupDirBase =            "BackupDirBase";
   static final String prefBackupDir =                "BackupDir";
   static final String prefBackupName =               "BackupName";
   static final String prefArchiveChecked =           "ArchiveChecked";
   static final String prefArchiveDirBase =           "ArchiveDirBase";
   static final String prefArchiveDir =               "ArchiveDir";
   static final String prefArchiveDirWin =            "ArchiveDirWin";

   // Constants
   static final int    kb =                1024;
   static final String nullStr =           SystemAttributes.nullStr;
   static final String space =             SystemAttributes.space;
   static final String tab =               SystemAttributes.tab;
   static final String divider =           SystemAttributes.dividerStr;
   static final String fs =                SystemAttributes.fileSeparator;
   static final String dataPrompt =        SystemAttributes.dataPrompt;
   static final String replacementChar =   SystemAttributes.replacementChar;  //"%"
   static final String sizePre =           space + space + "[";
   static final String sizePost =          space + AppProperties.getProperty("FilterMarkerUnits") + "]";
   static final String filterMarkerPre =   AppProperties.getProperty("FilterMarkerPre");    //usually: "["
   static final String filterMarkerPost =  AppProperties.getProperty("FilterMarkerPost");   //usually: "]"
   static final String filterMarkerSkip =  AppProperties.getProperty("FilterMarkerSkip");   //usually: "-"
   static final String filterMarkerSize =  AppProperties.getProperty("FilterMarkerSize");   //usually: ">"
   static final String filterMarkerUnits = AppProperties.getPropertyPadded("FilterMarkerUnits");  //usually: "KB"

   // User selections not owned by a UI control
   static List<String>
      zipItemList, zipIncludeList, zipExcludeList, zipExcludeFolderList, zipExcludeSizeList;

   // Command-line data
   static String  cmdBackupDir;
   static String  cmdBackupName;
   static String  cmdBackupPath;
   static boolean cmdDoArchive;
   static String  cmdArchiveDir;
   static String  cmdArchivePath;
   static boolean cmdFiltersOn;

   public static List<String> getProfilesNames() {
      List<String> names = UserPreferences.getProfileNames();
      if (names.isEmpty())
         names.add(AppProperties.getProperty(prefCurrentProfile));
      return names;
      }

   public static String saveSettings(SnapBackupFrame f) {
      UserPreferences.savePref(prefAppVersion,     SystemAttributes.appVersion);
      UserPreferences.savePref(prefCurrentProfile, f.getCurrentProfileName());
      UserPreferences.saveProfilePref(prefProfileName,              f.getCurrentProfileName());
      UserPreferences.saveProfilePref(prefSrcDataList,              list2StrList(zipItemList));
      UserPreferences.saveProfilePref(prefSrcDataIncludeList,       list2StrList(zipIncludeList));
      UserPreferences.saveProfilePref(prefSrcDataExcludeList,       list2StrList(zipExcludeList));
      UserPreferences.saveProfilePref(prefSrcDataExcludeFolderList, list2StrList(zipExcludeFolderList));
      UserPreferences.saveProfilePref(prefSrcDataExcludeSizeList,   list2StrList(zipExcludeSizeList));
      UserPreferences.saveProfilePref(prefBackupDir,                f.getDestBackupDirTextField().getText());
      UserPreferences.saveProfilePref(prefBackupName,               f.getDestBackupNameTextField().getText());
      UserPreferences.saveProfilePref(prefArchiveChecked,           f.getDestArchivePromptCheckBox().isSelected());
      UserPreferences.saveProfilePref(prefArchiveDir,               f.getDestArchiveDirTextField().getText());
      return UIProperties.current.msgSettingsSaved;
      }

   public static void saveShowLanguagesSetting(SnapBackupFrame f) {
      UserPreferences.savePref(prefShowLanguages, f.getShowLanguages());
      }

   public static void saveFiltersEnabledSetting(SnapBackupFrame f) {
      UserPreferences.savePref(prefFiltersEnabled, f.getFiltersEnabled());
      }

   public static void saveShowProfilesSetting(SnapBackupFrame f) {
      UserPreferences.savePref(prefShowProfiles, f.getProfilesEnabled());
      }

   public static String restoreDefaultSettings(SnapBackupFrame f) {
      zipItemList = strList2List(AppProperties.getProperty(prefSrcDataList));
      zipIncludeList =       strList2List(nullStr, zipItemList.size());
      zipExcludeList =       strList2List(nullStr, zipItemList.size());
      zipExcludeFolderList = strList2List(nullStr, zipItemList.size());
      zipExcludeSizeList =   strList2List(nullStr, zipItemList.size());
      buildZipListModel(f.getSrcZipListModel(), f);
      f.getDestBackupDirTextField().setText(AppProperties.getProperty(prefBackupDir));
      f.getDestBackupNameTextField().setText(AppProperties.getProperty(prefBackupName));
      f.getDestArchivePromptCheckBox().setSelected(UserPreferences.defaultPrefArchiveChecked);
      f.getDestArchiveDirTextField().setText(AppProperties.getProperty(prefArchiveDir));
      updateControls(f);
      return UIProperties.current.msgSettingsRestored;
      }

   public static void updateSrcButtons(SnapBackupFrame f) {
       boolean itemSelected = f.getSrcZipList().getSelectedIndex() != -1;
       f.getSrcRemoveButton().setEnabled(itemSelected);
       f.getSrcFilterButton().setEnabled(itemSelected);
       f.getSrcFilterButton().setVisible(f.getFiltersEnabled());
       }

   public static void updateDestPaths(SnapBackupFrame f) {
      f.getDestBackupPathLabel().setText(calcDestBackupPath(f));
      f.getDestArchivePathLabel().setText(calcDestArchivePath(f));
      }

   public static void updateArchiveDir(SnapBackupFrame f) {
      final boolean archiveOn = f.getDestArchivePromptCheckBox().isSelected();
      f.getDestArchiveDirTextField().setEnabled(archiveOn);
      f.getDestArchiveChooserButton().setEnabled(archiveOn);
      f.getDestArchiveTagLabel().setEnabled(archiveOn || !SystemAttributes.evilWinSys);  //fix disappearing icon problem on Macs and Linux
      f.getDestArchivePathLabel().setEnabled(archiveOn);
      f.getDestArchiveDirTextField().repaint();
      f.getDestArchiveChooserButton().repaint();
      f.getDestArchiveTagLabel().repaint();
      f.getDestArchivePathLabel().repaint();
      }

   public static void updateControls(SnapBackupFrame f) {
      updateSrcButtons(f);
      updateDestPaths(f);
      updateArchiveDir(f);
      }

   static String list2StrList(List<String> list) {
      // Converts multi-line data (List) into a single long string using the "splitStr" delimiter
      StringBuffer strList = new StringBuffer();
      boolean firstLine = true;
      for (String line : list) {
         strList.append(firstLine ? line : SystemAttributes.splitStr + line);
         firstLine = false;
         }
      return strList.toString();
      }

   static List<String> strList2List(String strList, int minSize) {
      // Creates a List of lines from a string of multi-line data delimited with "splitStr"
      List<String> list =
            new ArrayList<String>(Arrays.asList(strList.split(SystemAttributes.splitStr)));
      if (nullStr.equals(strList))
         list = new ArrayList<String>();
      while (list.size() < minSize)
         list.add(nullStr);
      return list;
      }

   static List<String> strList2List(String strList) {
       return strList2List(strList, 0);
       }

   public static void addZipItem(String zipItem, SnapBackupFrame f) {
      zipItemList.add(zipItem);  //add to data store
      zipIncludeList.add(nullStr);
      zipExcludeList.add(nullStr);
      zipExcludeFolderList.add(nullStr);
      zipExcludeSizeList.add(nullStr);
      f.getSrcZipListModel().addElement(zipItem);  //add to UI control
      f.getSrcZipList().setSelectedIndex(zipItemList.size() - 1);
      }

   public static void removeCurrentZipItem(SnapBackupFrame f) {
      int loc = f.getSrcZipList().getSelectedIndex();
      zipItemList.remove(loc);  //remove from data store
      zipIncludeList.remove(loc);
      zipExcludeList.remove(loc);
      zipExcludeFolderList.remove(loc);
      zipExcludeSizeList.remove(loc);
      f.getSrcZipListModel().remove(loc);  //remove from UI control
      if (zipItemList.isEmpty())
         f.getSrcRemoveButton().setEnabled(false);
      else
         f.getSrcZipList().setSelectedIndex(
            Math.min(loc, zipItemList.size() - 1));
      }

   public static String buildZipListLine(String zipItem, String includeFilter,
         String excludeFilter, String excludeFoldersFilter, String sizeFilter,
         SnapBackupFrame f) {
      String line = zipItem;
      if (f.getFiltersEnabled()) {
         line = line + tab;
         if (!nullStr.equals(includeFilter))
            line = line + filterMarkerPre + includeFilter + filterMarkerPost + space + space;
         String exclude = nullStr;
         boolean first = true;
         if (!nullStr.equals(excludeFilter)) {
            exclude = exclude + (first ? nullStr : ", ") + excludeFilter;
            first = false;
            }
         if (!nullStr.equals(excludeFoldersFilter)) {
            exclude = exclude + (first ? nullStr : ", ") + fs + excludeFoldersFilter + fs;
            first = false;
            }
         if (!nullStr.equals(sizeFilter)) {
            exclude = exclude + (first ? nullStr : ", ") + filterMarkerSize +
               sizeFilter + filterMarkerUnits;
            first = false;
            }
         if (!first)
            line = line + filterMarkerSkip + filterMarkerPre + exclude + filterMarkerPost;
         }
      return line;
      }

   public static void buildZipListModel(DefaultListModel<String> zipListModel, SnapBackupFrame f) {
      zipListModel.clear();
      int loc = 0;
      for (String zipItem : zipItemList) {
         zipListModel.addElement(buildZipListLine(zipItem,
            zipIncludeList.get(loc), zipExcludeList.get(loc),
            zipExcludeFolderList.get(loc), zipExcludeSizeList.get(loc), f));
         loc = loc + 1;
         }
      }

   public static String getCurrentZipItem(SnapBackupFrame f) {
      return zipItemList.get(f.getSrcZipList().getSelectedIndex());
      }

   public static String getCurrentZipIncludeFilter(SnapBackupFrame f) {
      return zipIncludeList.get(f.getSrcZipList().getSelectedIndex());
      }

   public static String getCurrentZipExcludeFilter(SnapBackupFrame f) {
      return zipExcludeList.get(f.getSrcZipList().getSelectedIndex());
      }

   public static String getCurrentZipExcludeFolderFilter(SnapBackupFrame f) {
      return zipExcludeFolderList.get(f.getSrcZipList().getSelectedIndex());
      }

   public static String getCurrentZipExcludeSizeFilter(SnapBackupFrame f) {
      return zipExcludeSizeList.get(f.getSrcZipList().getSelectedIndex());
      }

   public static void setCurrentZipFilter(String includeFilter,
      String excludeFilter, String excludeFolderFilter, String sizeFilter, SnapBackupFrame f) {
      int loc = f.getSrcZipList().getSelectedIndex();
      zipIncludeList.set(loc, includeFilter);
      zipExcludeList.set(loc, excludeFilter);
      zipExcludeFolderList.set(loc, excludeFolderFilter);
      zipExcludeSizeList.set(loc, sizeFilter);
      f.getSrcZipListModel().set(loc, buildZipListLine(getCurrentZipItem(f),
         includeFilter, excludeFilter, excludeFolderFilter, sizeFilter, f));
      }

   static void loadZipList(SnapBackupFrame f) {
      // Zip list data store
      zipItemList = strList2List(UserPreferences.readProfilePref(prefSrcDataList));
      zipIncludeList = strList2List(UserPreferences.readProfilePref(prefSrcDataIncludeList), zipItemList.size());
      zipExcludeList = strList2List(UserPreferences.readProfilePref(prefSrcDataExcludeList), zipItemList.size());
      zipExcludeFolderList = strList2List(UserPreferences.readProfilePref(prefSrcDataExcludeFolderList), zipItemList.size());
      zipExcludeSizeList = strList2List(UserPreferences.readProfilePref(prefSrcDataExcludeSizeList), zipItemList.size());

      // Zip list ui control
      if (f != null)
         buildZipListModel(f.getSrcZipListModel(), f);
      }

   public static void loadProfile(SnapBackupFrame f) {
      loadZipList(f);
      f.getDestBackupDirTextField().setText(UserPreferences.readProfilePref(prefBackupDir));
      f.getDestBackupNameTextField().setText(UserPreferences.readProfilePref(prefBackupName));
      f.getDestArchivePromptCheckBox().setSelected(UserPreferences.readProfilePref(prefArchiveChecked, UserPreferences.defaultPrefArchiveChecked));
      f.getDestArchiveDirTextField().setText(UserPreferences.readProfilePref(prefArchiveDir));
      updateControls(f);
      }

   public static void initSettings(SnapBackupFrame f) {
      initSupplimentalSettings();
      //SnapBackupFrame.setProfilesDropDown(UserPreferences.readPref(prefProfileList).split(SystemAttributes.splitStr));
      f.initLanguagesUI(UserPreferences.readPref(prefShowLanguages,  UserPreferences.defaultPrefShowLanguages));
      f.initFiltersUI(  UserPreferences.readPref(prefFiltersEnabled, UserPreferences.defaultPrefFiltersEnabled));
      f.initProfilesUI( UserPreferences.readPref(prefShowProfiles,   UserPreferences.defaultPrefShowProfiles));
      f.selectProfile(  UserPreferences.readPref(prefCurrentProfile));
      loadProfile(f);  //usually redudent but needed in case profile happens to be 1st on the list
      }

   public static void initSettings() {
      // Command-line
      initSupplimentalSettings();
      loadZipList(null);
      }

   public static void initSupplimentalSettings() {
      //String backupDir = fs +
      //   AppProperties.getProperty(prefBackupDirBase) + fs +
      //   SystemAttributes.userName + fs;
      String backupDir = SystemAttributes.userHomeDir + fs +
         AppProperties.getProperty(prefBackupDirBase);
      String archiveDir = fs + AppProperties.getProperty(prefArchiveDirBase).replace(
         replacementChar.charAt(0), fs.charAt(0)) + fs;
      if (SystemAttributes.evilWinSys) {
         //backupDir = SystemAttributes.evilWinDrive + backupDir;
         archiveDir = AppProperties.getProperty(prefArchiveDirWin);
         }
      String srcDataList = SystemAttributes.userDocsDir;
      String srcDataEmpty = nullStr;
      if (!SystemAttributes.userDocsDir.equals(SystemAttributes.userDesktopDir)) {
         srcDataList = srcDataList + SystemAttributes.splitStr + SystemAttributes.userDesktopDir;
         srcDataEmpty = srcDataEmpty + SystemAttributes.splitStr + nullStr;
         }
      AppProperties.addSupplimentalProperty(prefSrcDataList,              srcDataList);
      AppProperties.addSupplimentalProperty(prefSrcDataIncludeList,       srcDataEmpty);
      AppProperties.addSupplimentalProperty(prefSrcDataExcludeList,       srcDataEmpty);
      AppProperties.addSupplimentalProperty(prefSrcDataExcludeFolderList, srcDataEmpty);
      AppProperties.addSupplimentalProperty(prefSrcDataExcludeSizeList,   srcDataEmpty);
      AppProperties.addSupplimentalProperty(prefBackupDir,                backupDir);
      AppProperties.addSupplimentalProperty(prefArchiveDir,               archiveDir);
      AppProperties.addSupplimentalProperty(Options.prefSpacer,           Options.spacerDefault);
      AppProperties.addSupplimentalProperty(Options.prefOrder,            Options.orderDefault);
      AppProperties.addSupplimentalProperty(Options.prefYear,             Options.yearDefault);
      AppProperties.addSupplimentalProperty(Options.prefSeparator,        Options.separatorDefault);
      AppProperties.addSupplimentalProperty(Options.prefAskBackup,        Options.askBackupDefault);
      AppProperties.addSupplimentalProperty(Options.prefAskArchive,       Options.askArchiveDefault);
      AppProperties.addSupplimentalProperty(Options.prefNumRowsSrc,       Options.numRowsSrcDefault);
      AppProperties.addSupplimentalProperty(Options.prefNumRowsLog,       Options.numRowsLogDefault);
      AppProperties.addSupplimentalProperty(Options.prefShowSkipped,      Options.showSkippedDefault);
      AppProperties.addSupplimentalProperty(Options.prefNumLargestFiles,  Options.numLargestFilesDefault);
      }

   static String calcDestPath(String backupDir, String backupName) {
      return backupDir + (backupDir.endsWith(fs) ? nullStr : fs) + backupName +
         UserPreferences.readPref(Options.prefSpacer) +
         DateStamp.todaysDateStamp() + ".zip";
      }

   static String calcDestPath(SnapBackupFrame f, String dir) {
      return calcDestPath(dir, f.getDestBackupNameTextField().getText());
      }

   static String calcDestBackupPath(SnapBackupFrame f) {
      return calcDestPath(f, f.getDestBackupDirTextField().getText());
      }

   static String calcDestBackupPath() {
      return calcDestBackupPath(SnapBackupFrame.current);
      }

   static String calcDestArchivePath(SnapBackupFrame f) {
      return calcDestPath(f, f.getDestArchiveDirTextField().getText());
      }

   static String calcDestArchivePath() {
      return calcDestArchivePath(SnapBackupFrame.current);
      }

   public static void exit() {
      System.exit(0);
      }

   static Calendar startBackup;
   public static void logTimeStart() {
      startBackup = Calendar.getInstance();
      }
   public static void logTimeEnd() {
      long elapsedMills = Calendar.getInstance().getTimeInMillis() - startBackup.getTimeInMillis();
      long minutes = elapsedMills/60000;
      Locale locale = new Locale(UserPreferences.readLocalePref());
      NumberFormat nf = NumberFormat.getNumberInstance(locale);
      NumberFormat nfp = NumberFormat.getPercentInstance(locale);
      NumberFormat nfkb = NumberFormat.getNumberInstance(locale);
      nf.setMaximumFractionDigits(2);
      nfkb.setMaximumFractionDigits(0);
      nfp.setMinimumFractionDigits(1);
      nfp.setMaximumFractionDigits(1);
      String seconds = nf.format((elapsedMills - 60000*(elapsedMills/60000))/1000.0);
      String memory = nfp.format(1.0*Runtime.getRuntime().totalMemory()/Runtime.getRuntime().maxMemory());
      String memoryKB = nfkb.format(Runtime.getRuntime().totalMemory()/kb);
      Logger.logMsg(UIProperties.current.logMsgMemoryUsed + memory +
         sizePre + memoryKB + sizePost + space + divider + space +
         UIProperties.current.logMsgElapsedTime + space +
         minutes + UIProperties.current.logMsgMinutes +
         seconds + UIProperties.current.logMsgSeconds
         );
      }

   public static void doBackup() {
      final ZipEngine zip = new ZipEngine();
      final BackupProgressDialog backupProgress = new BackupProgressDialog(zip);
      zip.setBackupProgressDialog(backupProgress);
      class BackupSwingWorker extends SwingWorker<String, Object> {
         @Override
         public String doInBackground() {
            logTimeStart();
            Logger.spacer();
            Logger.logMsg(UIProperties.current.logMsgStart);
            List<List<String>> filterInfo = new ArrayList<List<String>>();
            filterInfo.add(zipIncludeList);
            filterInfo.add(zipExcludeList);
            filterInfo.add(zipExcludeFolderList);
            filterInfo.add(zipExcludeSizeList);
            zip.zipItems(zipItemList, calcDestBackupPath(),
               SnapBackupFrame.current.getFiltersEnabled(), filterInfo);
            if (SnapBackupFrame.current.getDestArchivePromptCheckBox().isSelected() && !zip.isAbortSet())
               FileSys.copyFile(calcDestBackupPath(), calcDestArchivePath(), backupProgress,
                  zip, UserPreferences.readPref(Options.prefAskArchive).equals(Options.askNo));
            if (zip.isAbortSet())
               Logger.logMsg(UIProperties.current.logMsgAborted);
            logTimeEnd();
            Logger.logMsg(UIProperties.current.logMsgEnd);
            SnapBackupFrame.current.getLogScrollPane().getHorizontalScrollBar().setValue(0);
            return "ok";
            }
         @Override
         protected void done() {
            if (!zip.isAbortSet()) {
               backupProgress.done();
               SnapBackupFrame.current.getExitButton().grabFocus();
               }
            TimerTask task = new TimerTask() {
               @Override
               public void run() { backupProgress.dispose(); }
               };
            new Timer().schedule(task, 800);  //0.8 second delay
            }
         }
      new BackupSwingWorker().execute();
      //UIUtilities.centerDialog(backupProgress);
      backupProgress.setLocationRelativeTo(SnapBackupFrame.current);
      backupProgress.setVisible(true);
      }

   static void loadCmdLineData() {
      cmdBackupDir =   UserPreferences.readProfilePref(prefBackupDir);
      cmdBackupName =  UserPreferences.readProfilePref(prefBackupName);
      cmdBackupPath =  calcDestPath(cmdBackupDir, cmdBackupName);
      cmdDoArchive =   UserPreferences.readProfilePref(prefArchiveChecked, UserPreferences.defaultPrefArchiveChecked);
      cmdArchiveDir =  UserPreferences.readProfilePref(prefArchiveDir);
      cmdArchivePath = calcDestPath(cmdArchiveDir, cmdBackupName);
      cmdFiltersOn =   UserPreferences.readPref(prefFiltersEnabled, UserPreferences.defaultPrefFiltersEnabled);
      }

   public static void doCmdLineList() {
      new UIProperties();
      UIProperties ui = UIProperties.current;
      List<String> names = DataModel.getProfilesNames();
      Logger.initOutput();
      Logger.logMsg(ui.headerCmdLine);
      Logger.logMsg(ui.profilesTitle + ": " + names.size());
      for (String profileName : names)
         Logger.logMsg("   " + Str.quote(profileName));
      Logger.logMsg(ui.profilesPrompt + " " + Str.quote(UserPreferences.readPref(prefCurrentProfile)));
      }

   public static void doCmdLineBackup(String profileName) {
      new UIProperties();
      final ZipEngine zip = new ZipEngine();
      UserPreferences.setCmdLineProfileName(profileName);
      initSettings();
      Logger.initOutput();
      Logger.logMsg(UIProperties.current.headerCmdLine);
      logTimeStart();
      Logger.logMsg(UIProperties.current.logMsgStart);
      Logger.logMsg(UIProperties.current.logMsgProfile + profileName);
      loadCmdLineData();
      //List[] filterInfo = {
      //   zipIncludeList, zipExcludeList, zipExcludeFolderList, zipExcludeSizeList };
      List<List<String>> filterInfo = new ArrayList<List<String>>();
         filterInfo.add(zipIncludeList);
         filterInfo.add(zipExcludeList);
         filterInfo.add(zipExcludeFolderList);
         filterInfo.add(zipExcludeSizeList);
      if (UserPreferences.profileInDB())
         zip.zipItems(zipItemList, cmdBackupPath, cmdFiltersOn, filterInfo);
      else
         zip.abortBackup(UIProperties.current.err30ProfileNotFound + dataPrompt + profileName);
      if (cmdDoArchive && !zip.isAbortSet())
         FileSys.copyFile(cmdBackupPath, cmdArchivePath, zip);
      if (zip.isAbortSet())
         Logger.logMsg(UIProperties.current.logMsgAborted);
      logTimeEnd();
      Logger.logMsg(UIProperties.current.logMsgEnd);
      }

}
