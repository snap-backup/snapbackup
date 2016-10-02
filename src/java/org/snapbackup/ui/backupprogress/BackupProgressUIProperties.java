////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// BackupProgressUIProperties.java                                            //
//                                                                            //
// Backup Progress UI Properties:                                             //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.backupprogress;

import org.snapbackup.settings.AppProperties;

public class BackupProgressUIProperties {

   //Constants
   public static final int progressMax = 20;

   //Backup Progress
   public static final String backupProgressTitle =      AppProperties.getProperty("BackupProgressTitle");
   public static final String backupProgressTag =        AppProperties.getPropertyPadded("BackupProgressTag");
   public static final String backupProgressFiles =      AppProperties.getPropertyPadded("BackupProgressFiles");
   public static final String backupProgressMemoryFree = AppProperties.getPropertyPadded("BackupProgressMemoryFree");
   public static final String backupProgressCancel =     AppProperties.getProperty("BackupProgressCancel");
   public static final String backupProgressDone =       AppProperties.getProperty("BackupProgressDone");
   public static final String backupProgressAborting =   AppProperties.getProperty("BackupProgressAborting");

}
