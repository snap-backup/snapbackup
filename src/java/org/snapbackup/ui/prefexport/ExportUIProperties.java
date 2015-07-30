////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ExportSettingsUIProperties.java                                            //
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
// Export Settings UI Properties:                                             //
//    This object..                                                           //
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
