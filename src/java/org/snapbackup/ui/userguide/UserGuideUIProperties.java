////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UserGuideUIProperties.java                                                 //
//                                                                            //
// User Guide UI Properties:                                                  //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.userguide;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class UserGuideUIProperties {

   // Constants for the user guide.
   static final String userGuideURL =        "html/snap-backup-user-guide.html";  //folder: com/snapbackup/ui/userguid/html/
   static final String userGuideErrMsgMIME = "text/html";
   static final int    userGuideSizeScaleX = 75;   //html box 75% of main window's width
   static final int    userGuideSizeScaleY = 100;  //html box 100% of main window's hieght
   static final String space =               SystemAttributes.space;
   static final String appVersion =          SystemAttributes.appVersion;

   // Data from the .properties file.
   final String userGuideHeader =      AppProperties.getProperty("UserGuideHeader");
   final String userGuideTitle =       SystemAttributes.appTitle + space + userGuideHeader;
   final String userGuideVersion =     AppProperties.getProperty("UserGuideVersion") + space + appVersion;
   final String userGuideButtonClose = AppProperties.getProperty("UserGuideButtonClose");
   final String userGuideErrMsg =      AppProperties.getProperty("UserGuideErrMsg");

}
