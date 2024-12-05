////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UIProperties.java                                                          //
//                                                                            //
// UI Properties:                                                             //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class UIProperties {

   // Constants
   //public static final int    backupCols = 50;
   public static final int    srcMinRows = 2;
   public static final int    srcDirCols = 45;
   public static final int    srcNameCols = 15;
   public static final int    archiveDirCols = 30;
   public static final int    logCols = 55;
   public static final int    logMinRows = 2;
   public static final char   standardWidthChar = 'm';  //determines column width
   public static final Font   standardFont = new JTextField().getFont();  //grab good font
   public static final Color  standardBGColor = new JPanel().getBackground();  //grab gray color
   public static final Color  emphasisColor = Color.blue;
   public static final String iconURL = "images/";
   public static final String snapBackupIconURL = iconURL + "snap-backup-icon.png";
   public static final String langIconURLpre = iconURL + "language-";
   public static final String langIconURLpost = ".png";
   public static final String logoIconURL = iconURL + "logo.png";
   //public static final String zipIconFileName = "ZipIcon.zip";
   public static final String space = SystemAttributes.space;
   public static final String newLine = SystemAttributes.newLine;

   //
   // From .properties File
   //

   // Check for Updates
   public final String updatesVersionYours =     AppProperties.getProperty("UpdatesVersionYours");
   public final String updatesVersionIsCurrent = AppProperties.getProperty("UpdatesVersionIsCurrent");
   public final String updatesVersionNew =       AppProperties.getProperty("UpdatesVersionNew");
   public final String updatesButtonDownload =   AppProperties.getProperty("UpdatesButtonDownload");
   public final String updatesButtonCancel =     AppProperties.getProperty("ButtonCancel");
   public final String updatesErrMsg1 =          AppProperties.getProperty("UpdatesErrMsg1");
   public final String updatesErrMsg2 =          AppProperties.getProperty("UpdatesErrMsg2");

   // Header
   public final String appTitle =             SystemAttributes.appTitle;
   public final String header =               SystemAttributes.headerPre + appTitle + SystemAttributes.headerMid + SystemAttributes.headerPost;
   //public final String splashHeader =         SystemAttributes.headerPre + appTitle + SystemAttributes.headerMid + SystemAttributes.headerSplashTag + SystemAttributes.headerPost;
   public final String headerCmdLine =        appTitle + " v" + SystemAttributes.appVersion;

   // Menus (File Menu)
   public final String menuGroupFile =        AppProperties.getProperty("MenuGroupFile");

   public final String menuItemLangs =        AppProperties.getProperty("MenuItemLanguages");
   public final String menuItemLangsShow =    AppProperties.getProperty("MenuItemLanguagesShow");
   public final String menuItemLangsHide =    AppProperties.getProperty("MenuItemLanguagesHide");

   public final String menuItemFilters =      AppProperties.getProperty("MenuItemFilters");
   public final String menuItemFiltersOn =    AppProperties.getProperty("MenuItemFiltersOn");
   public final String menuItemFiltersOff =   AppProperties.getProperty("MenuItemFiltersOff");

   public final String menuItemProfiles =     AppProperties.getProperty("MenuItemProfiles");
   public final String menuItemProfilesOn =   AppProperties.getProperty("MenuItemProfilesOn");
   public final String menuItemProfilesOff =  AppProperties.getProperty("MenuItemProfilesOff");

   public final String menuItemExport =       AppProperties.getProperty("MenuItemExport");
   public final String menuItemImport =       AppProperties.getProperty("MenuItemImport");
   public final String menuItemOptions =      AppProperties.getProperty("MenuItemOptions");
   public final String menuItemExit =         AppProperties.getProperty("MenuItemExit");

   // Menus (Help Menu)
   public final String menuGroupHelp =        AppProperties.getProperty("MenuGroupHelp");
   public final String menuItemGuide =        AppProperties.getProperty("MenuItemGuide");
   public final String menuItemUpdates =      AppProperties.getProperty("MenuItemUpdates");
   public final String menuItemAbout =        AppProperties.getProperty("MenuItemAbout");

   // Profiles
   public final String profilesOffMsg =       AppProperties.getProperty("ProfilesOffMsg");
   public final String profilesErrMsg =       AppProperties.getProperty("ProfilesErrMsg");
   public final String profilesTitle =        AppProperties.getProperty("ProfilesTitle");
   public final String profilesPrompt =       AppProperties.getProperty("ProfilesPrompt");
   public final String profilesNew =          AppProperties.getProperty("ProfilesButtonNew");
   public final String profilesDelete =       AppProperties.getProperty("ProfilesButtonDelete");
   public final String profilesAddTitle =     AppProperties.getProperty("ProfilesAddTitle");
   public final String profilesAddPrompt =    AppProperties.getProperty("ProfilesAddPrompt");
   public final String profilesAddMsgBlank =  AppProperties.getProperty("ProfilesAddMsgBlank");
   public final String profilesAddMsgExists = AppProperties.getProperty("ProfilesAddMsgExists");
   public final String profilesDeleteTitle =  AppProperties.getProperty("ProfilesDeleteTitle");
   public final String profilesDeletePrompt = AppProperties.getProperty("ProfilesDeletePrompt");

   // Source (Zip List)
   public final String srcTitle =             AppProperties.getPropertyPadded("SourceTitle");
   public final String srcPrompt =            AppProperties.getProperty("SourcePrompt");
   public final String srcAddFile =           AppProperties.getProperty("SourceAddFile");
   public final String srcAddFolder =         AppProperties.getProperty("SourceAddFolder");
   public final String srcAddCmd =            AppProperties.getProperty("SourceAddCommand");
   public final String srcRemove =            AppProperties.getProperty("SourceRemove");
   public final String srcFilter =            AppProperties.getProperty("SourceFilter");

   // Tip
   public final String tip =                  AppProperties.getProperty("Tip");
   public final String tipHelp =              AppProperties.getProperty("TipHelp");

   // Destination (Backup & Archive)
   public final String destTitle =            AppProperties.getPropertyPadded("DestinationTitle");
   public final String destBackupPrompt =     AppProperties.getProperty("DestinationBackupPrompt");
   //public final String destBackupChooser =    AppProperties.getProperty("DestinationBackupChooser");
   public final String destBackupCmd =        AppProperties.getProperty("DestinationBackupCommand");
   public final String destBackupTag =        AppProperties.getProperty("DestinationBackupTag");
   public final String destBackupNamePrompt = AppProperties.getProperty("DestinationBackupNamePrompt");
   public final String destArchivePrompt =    AppProperties.getProperty("DestinationArchivePrompt");
   //public final String destArchiveChooser =   AppProperties.getProperty("DestinationArchiveChooser");
   public final String destArchiveCmd =       AppProperties.getProperty("DestinationArchiveCommand");
   public final String destArchiveTag =       AppProperties.getProperty("DestinationArchiveTag");

   // Log
   public final String logTitle =             AppProperties.getPropertyPadded("LogTitle");
   public final String logMsgStart =          AppProperties.getProperty("LogMsgStart");
   public final String logMsgEnd =            AppProperties.getProperty("LogMsgEnd");
   public final String logMsgProfile =        AppProperties.getProperty("LogMsgProfile") + space;
   public final String logMsgFiltersOn =      AppProperties.getProperty("LogMsgFiltersOn");
   public final String logMsgCreating =       AppProperties.getProperty("LogMsgCreating") + space;
   public final String logMsgRootPath =       AppProperties.getProperty("LogMsgRootPath");
   public final String logMsgZipping =        AppProperties.getPropertyPadded("LogMsgZipping");
   public final String logMsgSkipping =       AppProperties.getPropertyPadded("LogMsgSkipping");
   public final String logMsgFolder =         AppProperties.getPropertyPadded("LogMsgFolder");
   public final String logMsgFilesZipped =    AppProperties.getProperty("LogMsgFilesZipped");
   public final String logMsgLargestFiles =   AppProperties.getProperty("LogMsgLargestFiles");
   public final String logMsgBackupCreated =  AppProperties.getProperty("LogMsgBackupCreated");
   public final String logMsgArchivedBackup = AppProperties.getProperty("LogMsgArchivedBackup");
   public final String logMsgElapsedTime =    AppProperties.getProperty("LogMsgElapsedTime");
   public final String logMsgMinutes =        AppProperties.getPropertyPadded("LogMsgMinutes");
   public final String logMsgSeconds =        AppProperties.getPropertyPadded("LogMsgSeconds");
   public final String logMsgMemoryUsed =     AppProperties.getProperty("LogMsgMemoryUsed") + space;
   public final String logMsgAborted =        AppProperties.getProperty("LogMsgAborted");

   // Buttons
   public final String buttonReset =          AppProperties.getProperty("ButtonReset");
   public final String buttonDoBackup =       AppProperties.getProperty("ButtonDoBackup");
   public final String buttonExit =           AppProperties.getProperty("ButtonExit");

   // File Utilties
   public final String fileUtilTitleBackupFolder =  AppProperties.getProperty("FileUtilityTitleBackupFolder");
   public final String fileUtilTitleBackupFile =    AppProperties.getProperty("FileUtilityTitleBackupFile");
   public final String fileUtilTitleArchiveFolder = AppProperties.getProperty("FileUtilityTitleArchiveFolder");
   public final String fileUtilTitleArchiveFile =   AppProperties.getProperty("FileUtilityTitleArchiveFile");
   public final String fileUtilCreateFolderMsg =    AppProperties.getProperty("FileUtilityCreateFolderMsg");
   public final String fileUtilCreateFolderButton = AppProperties.getProperty("FileUtilityCreateFolderButton");
   public final String fileUtilOverwriteMsg =       AppProperties.getProperty("FileUtilityOverwriteMsg");
   public final String fileUtilOverwriteButton =    AppProperties.getProperty("FileUtilityOverwriteButton");
   public final String fileUtilButtonAbort =        AppProperties.getProperty("FileUtilityButtonAbort");

   // Messages
   public final String msgSettingsRestored = AppProperties.getProperty("MsgSettingsRestored");

   // Errors
   public final String errPromptTitle =          AppProperties.getProperty("ErrPromptTitle");
   public final String errPromptContinue =       AppProperties.getProperty("ErrPromptContinue");
   public final String errPromptAbort =          AppProperties.getProperty("ErrPromptAbort");
   public final String err01CreatingBackupFile = "*** ERROR #1: " +  AppProperties.getProperty("Err01CreatingBackupFile");
   public final String err02ItemNotFound =       "*** ERROR #2: " +  AppProperties.getProperty("Err02ItemNotFound");
   public final String err03ZippingFile =        "*** ERROR #3: " +  AppProperties.getProperty("Err03ZippingFile");
   public final String err04BackupFileMissing =  "*** ERROR #4: " +  AppProperties.getProperty("Err04BackupFileMissing");
   public final String err05NoArchiveFolder =    "*** ERROR #5: " +  AppProperties.getProperty("Err05NoArchiveFolder");
   public final String err06CannotWriteArchive = "*** ERROR #6: " +  AppProperties.getProperty("Err06CannotWriteArchive");
   public final String err07ArchiveNotFile =     "*** ERROR #7: " +  AppProperties.getProperty("Err07ArchiveNotFile");
   public final String err08ArchiveCanceled =    "*** ERROR #8: " +  AppProperties.getProperty("Err08ArchiveCanceled");
   public final String err09ArchiveProblem =     "*** ERROR #9: " +  AppProperties.getProperty("Err09ArchiveProblem");
   public final String err10CannotParseFilter =  "*** ERROR #10: " + AppProperties.getProperty("Err10CannotParseFilter");
   public final String err20BrowserFailure =     "*** ERROR #20: " + AppProperties.getProperty("Err20BrowserFailure");
   public final String err30ProfileNotFound =    "*** ERROR #30: " + AppProperties.getProperty("Err30ProfileNotFound");
   public final String err40BackupTooLarge =     "*** ERROR #40: " + AppProperties.getProperty("Err40BackupTooLarge");

   public static UIProperties current;

   public UIProperties() {
      current = this;  //todo: AssignmentToNonFinalStatic
      }

}
