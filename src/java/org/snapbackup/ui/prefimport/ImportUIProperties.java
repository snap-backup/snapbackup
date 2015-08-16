////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ImportSettingsUIProperties.java                                            //
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
// Import Settings UI Properties:                                             //
//    This object..                                                           //
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
