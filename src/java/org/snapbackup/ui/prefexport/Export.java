////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Export.java                                                                //
//                                                                            //
// Export Settings Dialog:                                                    //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.prefexport;

import org.snapbackup.business.ExportPrefs;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.AppProperties;

public class Export {

   // Constants
   public static final String prefSettingsFileName = "ExportFileName";
   public static final int    fileNameCols =         40;

   // Setup
   public final String Settings = AppProperties.getProperty("ExportSettings");
   public final String defaultSettingsFileName =
      SystemAttributes.userDocsDir + SystemAttributes.fileSeparator +
      SystemAttributes.appName + Settings + ExportPrefs.xmlExtension;

}
