////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ImportSettingsUIProperties.java                                            //
//                                                                            //
// Import Settings UI Properties:                                             //
//    This object..                                                           //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.prefimport;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

class ImportUIProperties {

   final String title =            AppProperties.getProperty("ImportTitle");

   final String locationTitle =    AppProperties.getPropertyPadded("ImportLocationTitle");
   final String locationPrompt =   AppProperties.getProperty("ImportLocationPrompt");
   final String locationCmd =      AppProperties.getProperty("ImportLocationCommand");
   final String locationDetails =  AppProperties.getProperty("ImportLocationDetails");
   final String locationWarning =  SystemAttributes.startHtml + "<b>" +
         AppProperties.getProperty("ImportWarning") + "</b> " +
         AppProperties.getProperty("ImportWarningMessage") + SystemAttributes.endHtml;

   final String buttonCancel =     AppProperties.getProperty("ButtonCancel");
   final String buttonImport =     AppProperties.getProperty("ImportButtonAction");

   final String msgSuccess =       AppProperties.getProperty("ImportMsgSuccess");

}
