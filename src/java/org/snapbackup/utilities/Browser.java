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

public abstract class Browser {

   public static void open(String url) {
      try {
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         }
      catch (java.io.IOException e) {
         }
      }

}
