////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ExportSettingsUIProperties.java                                            //
//                                                                            //
// Export Settings UI Properties:                                             //
//    This object..                                                           //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.prefexport;

import org.snapbackup.settings.AppProperties;

class ExportUIProperties {

   final String title =            AppProperties.getProperty("ExportTitle");

   final String locationTitle =    AppProperties.getPropertyPadded("ExportLocationTitle");
   final String locationPrompt =   AppProperties.getProperty("ExportLocationPrompt");
   final String locationDetails1 = AppProperties.getProperty("ExportLocationDetails1");
   final String locationReset =    AppProperties.getProperty("ExportLocationReset");
   final String locationDetails2 = AppProperties.getProperty("ExportLocationDetails2");
   final String locationCmd =      AppProperties.getProperty("ExportLocationCommand");

   final String buttonCancel =     AppProperties.getProperty("ButtonCancel");
   final String buttonExport =     AppProperties.getProperty("ExportButtonAction");

   final String msgSuccess =       AppProperties.getProperty("ExportMsgSuccess");

}
