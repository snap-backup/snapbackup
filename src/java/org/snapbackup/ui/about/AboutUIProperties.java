////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AboutUIProperties.java                                                     //
//                                                                            //
// About UI Properties                                                        //
//    This object....                                                         //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.about;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class AboutUIProperties {

   static final String space =  SystemAttributes.space;
   //
   // From .properties file
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
   public final String aboutButtonWeb =    AppProperties.getProperty("AboutButtonWeb");
   public final String aboutButtonClose =  AppProperties.getProperty("AboutButtonClose");

}
