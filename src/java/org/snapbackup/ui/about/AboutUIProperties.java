////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AboutUIProperties.java                                                     //
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
// About UI Properties                                                        //
//    This object....                                                         //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.about;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class AboutUIProperties {

   static final String space =  SystemAttributes.space;
   //
   //From .properties File
   //
   public final String aboutTitle =        AppProperties.getPropertyPadded("AboutTitle");
   public final String aboutAppTitle =     AppProperties.getProperty("ApplicationTitle");
   public final String aboutVersion =      AppProperties.getProperty("AboutVersion") + space + SystemAttributes.appVersion;
   public final String aboutCreatedBy =    AppProperties.getProperty("AboutCreatedBy") + space + SystemAttributes.appAuthors;
   public final String aboutTranslatedBy = AppProperties.getProperty("AboutTranslatedBy");
   public final String aboutAnd =          AppProperties.getProperty("AboutAnd");
   public final String aboutLicense =      AppProperties.getProperty("AboutLicense");
   public final String aboutDownload =     AppProperties.getProperty("AboutDownload");
   public final String aboutCfgTitle =     AppProperties.getPropertyPadded("AboutConfigurationTitle");
   public final String aboutContact =      AppProperties.getProperty("AboutContactInfo");
   public final String aboutButtonWeb =    AppProperties.getProperty("AboutButtonWeb");
   public final String aboutButtonClose =  AppProperties.getProperty("AboutButtonClose");

}
