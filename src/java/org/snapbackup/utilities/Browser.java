////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// System.java                                                                //
//                                                                            //
// String:                                                                    //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

import java.io.IOException;
import org.snapbackup.logger.Logger;

public abstract class Browser {

   public static void open(String url) {
      try {
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         }
      catch (IOException e) {
         Logger.logMsg(e.toString());
         }
      }

}
