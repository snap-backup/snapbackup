////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UIProperties.java                                                          //
//                                                                            //
// GNU General Public License:                                                //
// This program is free software; you can redistribute it and/or modify it    //
// under the terms of the GNU General Public License as published by the      //
// Free Software Foundation; either version 2 of the License, or (at your     //
// option) any later version.                                                 //
//                                                                            //
// This program is distributed in the hope that it will be useful, but        //
// WITHOUT ANY WARRANTY; without even the implied warranty of                 //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                       //
//                                                                            //
// See the GNU General Public License at http://www.gnu.org for more          //
// details.                                                                   //
//                                                                            //
// Copyright (c) individual contributors to the Snap Backup project           //
// Snap Backup is a registered trademark of Center Key                        //
// http://snapbackup.org                                                      //
//                                                                            //
// UI Properties:                                                             //
//    This object...                                                          //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class UIProperties {

   //Constants
   //public static final int    backupCols = 50;
   public static final int    srcMinRows = 2;
   public static final int    srcDirCols = 45;
   public static final int    srcNameCols = 15;
   public static final int    archiveDirCols = 30;
   public static final int    logCols = 55;
   public static final int    logMinRows = 2;
   public static final char   standardWidthChar = 'm';  //determines column width
   public static final Font   standardFont = new JTextField().getFont();  //grab good font
   public static final Color  standardBGColor = new JPanel().getBackground();  // .getBackground();  //grab grey color
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
   //From .properties File
   //

   //Skin (Look and Feel)
   public static final UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
   public static String skinSetMsg =          AppProperties.getProperty("LookAndFeelSetMsg");
   public static String skinErrMsg =          AppProperties.getProperty("LookAndFeelErrMsg");

   //Check for Updates
   public final String updatesVersionYours =     AppProperties.getProperty("UpdatesVersionYours");
   public final String updatesVersionIsCurrent = AppProperties.getProperty("UpdatesVersionIsCurrent");
   public final String updatesVersionNew =       AppProperties.getProperty("UpdatesVersionNew");
   public final String updatesButtonDownload =   AppProperties.getProperty("UpdatesButtonDownload");
   public final String updatesButtonCancel =     AppProperties.getProperty("ButtonCancel");
   public final String updatesErrMsg1 =          AppProperties.getProperty("UpdatesErrMsg1");
   public final String updatesErrMsg2 =          AppProperties.getProperty("UpdatesErrMsg2");

   //Header
   public final String appTitle =             AppProperties.getProperty("ApplicationTitle");
   public final String appTitleLocalized =    AppProperties.getProperty("OptionalLocalizedApplicationTitle");
   public final String header =               SystemAttributes.headerPre + appTitle + SystemAttributes.headerMid + (appTitleLocalized.equals("") ? "" : "<br>" + appTitleLocalized) + SystemAttributes.headerPost;
   //public final String splashHeader =         SystemAttributes.headerPre + appTitle + SystemAttributes.headerMid + SystemAttributes.headerSplashTag + SystemAttributes.headerPost;
   public final String headerCmdLine =        appTitle + " " + SystemAttributes.appVersion + (appTitleLocalized.equals("") ? "" : " (" + appTitleLocalized + ")");

   //Menus (File Menu)
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

   public final String menuItemSkin =         AppProperties.getProperty("MenuItemLook");
   public final String menuItemExport =       AppProperties.getProperty("MenuItemExport");
   public final String menuItemImport =       AppProperties.getProperty("MenuItemImport");
   public final String menuItemOptions =      AppProperties.getProperty("MenuItemOptions");
   public final String menuItemExit =         AppProperties.getProperty("MenuItemExit");

   //Menus (Help Menu)
   public final String menuGroupHelp =        AppProperties.getProperty("MenuGroupHelp");
   public final String menuItemGuide =        AppProperties.getProperty("MenuItemGuide");
   public final String menuItemUpdates =      AppProperties.getProperty("MenuItemUpdates");
   public final String menuItemAbout =        AppProperties.getProperty("MenuItemAbout");

   //Filters
   //public final String FiltersButton =        AppProperties.getProperty("FiltersButton");
   //public final String FiltersTitle =         AppProperties.getProperty("FiltersTitle");
   //public final String FiltersSave =          AppProperties.getProperty("FiltersButtonSave");
   //public final String FiltersDelete =        AppProperties.getProperty("FiltersButtonDelete");
   //public final String FiltersCancel =        AppProperties.getProperty("ButtonCancel");

   //Profiles
   public final String profilesOffMsg =       AppProperties.getProperty("ProfilesOffMsg");
   public final String profilesErrMsg =       AppProperties.getProperty("ProfilesErrMsg");
   public final String profilesTitle =        AppProperties.getPropertyPadded("ProfilesTitle");
   public final String profilesPrompt =       AppProperties.getProperty("ProfilesPrompt");
   public final String profilesNew =          AppProperties.getProperty("ProfilesButtonNew");
   public final String profilesDelete =       AppProperties.getProperty("ProfilesButtonDelete");
   public final String profilesAddTitle =     AppProperties.getProperty("ProfilesAddTitle");
   public final String profilesAddPrompt =    AppProperties.getProperty("ProfilesAddPrompt");
   public final String profilesAddMsgBlank =  AppProperties.getProperty("ProfilesAddMsgBlank");
   public final String profilesAddMsgExists = AppProperties.getProperty("ProfilesAddMsgExists");
   public final String profilesDeleteTitle =  AppProperties.getProperty("ProfilesDeleteTitle");
   public final String profilesDeletePrompt = AppProperties.getProperty("ProfilesDeletePrompt");

   //Source (Zip List)
   public final String srcTitle =             AppProperties.getPropertyPadded("SourceTitle");
   public final String srcPrompt =            AppProperties.getProperty("SourcePrompt");
   public final String srcAddFile =           AppProperties.getProperty("SourceAddFile");
   public final String srcAddFolder =         AppProperties.getProperty("SourceAddFolder");
   public final String srcAddCmd =            AppProperties.getProperty("SourceAddCommand");
   public final String srcRemove =            AppProperties.getProperty("SourceRemove");
   public final String srcFilter =            AppProperties.getProperty("SourceFilter");

   //Tip
   public final String tip =                  AppProperties.getProperty("Tip");
   public final String tipHelp =              AppProperties.getProperty("TipHelp");

   //Destination (Backup & Archive)
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

   //Log
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

   //Buttons
   public final String buttonSave =           AppProperties.getProperty("ButtonSave");
   public final String buttonReset =          AppProperties.getProperty("ButtonReset");
   public final String buttonDoBackup =       AppProperties.getProperty("ButtonDoBackup");
   public final String buttonExit =           AppProperties.getProperty("ButtonExit");

   //File Utilties
   public final String fileUtilTitleBackupFolder =  AppProperties.getProperty("FileUtilityTitleBackupFolder");
   public final String fileUtilTitleBackupFile =    AppProperties.getProperty("FileUtilityTitleBackupFile");
   public final String fileUtilTitleArchiveFolder = AppProperties.getProperty("FileUtilityTitleArchiveFolder");
   public final String fileUtilTitleArchiveFile =   AppProperties.getProperty("FileUtilityTitleArchiveFile");
   public final String fileUtilCreateFolderMsg =    AppProperties.getProperty("FileUtilityCreateFolderMsg");
   public final String fileUtilCreateFolderButton = AppProperties.getProperty("FileUtilityCreateFolderButton");
   public final String fileUtilOverwriteMsg =       AppProperties.getProperty("FileUtilityOverwriteMsg");
   public final String fileUtilOverwriteButton =    AppProperties.getProperty("FileUtilityOverwriteButton");
   public final String fileUtilButtonAbort =        AppProperties.getProperty("FileUtilityButtonAbort");

   //Messages
   public final String msgSettingsSaved =        AppProperties.getProperty("MsgSettingsSaved");
   public final String msgSettingsRestored =     AppProperties.getProperty("MsgSettingsRestoredLine1") + newLine +
                                                 AppProperties.getProperty("MsgSettingsRestoredLine2");
   public final String errPromptTitle =          AppProperties.getProperty("ErrPromptTitle");
   public final String errPromptContinue =       AppProperties.getProperty("ErrPromptContinue");
   public final String errPromptAbort =          AppProperties.getProperty("ErrPromptAbort");
   public final String err01CreatingBackupFile = AppProperties.getProperty("Err01CreatingBackupFile");
   public final String err02ItemNotFound =       AppProperties.getProperty("Err02ItemNotFound");
   public final String err03ZippingFile =        AppProperties.getProperty("Err03ZippingFile");
   public final String err04BackupFileMissing =  AppProperties.getProperty("Err04BackupFileMissing");
   public final String err05NoArchiveFolder =    AppProperties.getProperty("Err05NoArchiveFolder");
   public final String err06CannotWriteArchive = AppProperties.getProperty("Err06CannotWriteArchive");
   public final String err07ArchiveNotFile =     AppProperties.getProperty("Err07ArchiveNotFile");
   public final String err08ArchiveCanceled =    AppProperties.getProperty("Err08ArchiveCanceled");
   public final String err09ArchiveProblem =     AppProperties.getProperty("Err09ArchiveProblem");
   public final String err10CannotParseFilter =  AppProperties.getProperty("Err10CannotParseFilter");
   public final String err20BrowserFailure =     AppProperties.getProperty("Err20BrowserFailure");
   public final String err25PrintFailure =       AppProperties.getProperty("Err25PrintFailure");
   public final String err30ProfileNotFound =    AppProperties.getProperty("Err30ProfileNotFound");
   public final String err40BackupTooLarge =     AppProperties.getProperty("Err40BackupTooLarge");

   public static UIProperties current;
   public UIProperties() {
      skinSetMsg = AppProperties.getProperty("LookAndFeelSetMsg");
      skinErrMsg = AppProperties.getProperty("LookAndFeelErrMsg");
      current = this;
      }

}
