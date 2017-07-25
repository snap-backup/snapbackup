////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// OptionsUIProperties.java                                                   //
//                                                                            //
// Options UI Properties:                                                     //
//    This object..                                                           //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.options;

import org.snapbackup.settings.AppProperties;

class OptionsUIProperties {

   final String title =                     AppProperties.getProperty("OptionsTitle");

   final String fileFormatTitle =           AppProperties.getPropertyPadded("OptionsFileFormatTitle");
   final String fileFormatExample =         AppProperties.getProperty("OptionsFileFormatExample");
   final String fileFormatSpacerPrompt =    AppProperties.getProperty("OptionsFileFormatNameSpacerPrompt");
   final String fileFormatSpacerList =      AppProperties.getProperty("OptionsFileFormatNameSpacerList");
   final String fileFormatOrderPrompt =     AppProperties.getProperty("OptionsFileFormatDateOrderPrompt");
   final String fileFormatOrderYear =       AppProperties.getProperty("OptionsFileFormatDateOrderYear");
   final String fileFormatOrderMonth =      AppProperties.getProperty("OptionsFileFormatDateOrderMonth");
   final String fileFormatOrderDay =        AppProperties.getProperty("OptionsFileFormatDateOrderDay");
   final String fileFormatOrderNone =       AppProperties.getProperty("OptionsFileFormatDateOrderNone");
   final String[] orderList =     {fileFormatOrderYear,  fileFormatOrderMonth, fileFormatOrderDay};
   final String[] orderListNone = {fileFormatOrderYear,  fileFormatOrderMonth, fileFormatOrderDay, fileFormatOrderNone};
   final String fileFormatYearPrompt =      AppProperties.getProperty("OptionsFileFormatDateYearPrompt");
   final String fileFormatYear2Digits =     AppProperties.getProperty("OptionsFileFormatDateYear2Digits");
   final String fileFormatYear4Digits =     AppProperties.getProperty("OptionsFileFormatDateYear4Digits");
   final String fileFormatSeparatorPrompt = AppProperties.getProperty("OptionsFileFormatDateSeparatorPrompt");
   final String fileFormatSeparatorList =   AppProperties.getProperty("OptionsFileFormatDateSeparatorList");

   final String overwriteTitle =            AppProperties.getPropertyPadded("OptionsOverwriteTitle");
   final String overwriteBackupPrompt =     AppProperties.getProperty("OptionsOverwriteBackupFile");
   final String overwriteArchivePrompt =    AppProperties.getProperty("OptionsOverwriteArchiveFile");

   final String numRowsTitle =              AppProperties.getPropertyPadded("OptionsNumberOfRowsTitle");
   final String numRowsSrcPrompt =          AppProperties.getPropertyPadded("SourcePrompt");
   final String numRowsLogPrompt =          " " + AppProperties.getProperty("LogTitle") + ": ";

   final String msgLogInfoTitle =           AppProperties.getPropertyPadded("OptionsMessageLogInfoTitle");
   final String msgLogInfoSkippedPrompt =   AppProperties.getProperty("OptionsMessageLogInfoSkippedPrompt");
   final Integer[] largestFileOptions =     { 5, 10, 15, 20 };
   final String msgLogInfoLargestPrompt =   AppProperties.getProperty("OptionsMessageLogInfoLargestPrompt");

   final String buttonCancel =              AppProperties.getProperty("ButtonCancel");
   final String buttonDefaults =            AppProperties.getProperty("OptionsButtonDefaults");
   final String buttonOk =                  AppProperties.getProperty("ButtonOk");

}
