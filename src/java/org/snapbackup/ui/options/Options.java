////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Options.java                                                               //
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
// Snap Backup is a registered trademark of Center Key Software               //
// http://snapbackup.org                                                      //
//                                                                            //
// Options:                                                                   //
//    This object..                                                           //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.options;

public class Options {
   public static final String prefSpacer =        "OptionSpacer";
   public static final String spacerDefault =     "_";

   public static final String prefOrder =         "OptionOrder";
   public static final String orderDefault =      "012";  // 0=year, 1=month, 2=day (naturally sorts)

   public static final String prefYear =          "OptionYear";
   public static final String year2Digits =       "2";
   public static final String year4Digits =       "4";
   public static final String yearDefault =       year4Digits;

   public static final String prefSeparator =     "OptionSeparator";
   public static final String separatorDefault =  "-";

   public static final String askYes =            "yes";
   public static final String askNo =             "no";
   public static final String prefAskBackup =     "OptionAskBackup";
   public static final String askBackupDefault =  askNo;
   public static final String prefAskArchive =    "OptionAskArchive";
   public static final String askArchiveDefault = askYes;

   public static final String prefNumRowsSrc =    "OptionNumRowsSource";
   public static final String numRowsSrcDefault = "4";
   public static final String prefNumRowsLog =    "OptionNumRowsMsgLog";
   public static final String numRowsLogDefault = "5";

   public static final String skipYes =                "yes";
   public static final String skipNo =                 "no";
   public static final String prefShowSkipped =        "OptionShowSkippedFiles";
   public static final String showSkippedDefault =     skipYes;
   public static final String prefNumLargestFiles =    "OptionNumLargestFiles";
   public static final String numLargestFilesDefault = "3";
   }
