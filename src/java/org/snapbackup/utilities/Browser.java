////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// System.java                                                                //
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
// String:                                                                    //
//    This object...                                                          //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

public class Browser {

   public static void open(String url) {
      try {
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         }
      catch (java.io.IOException e) {
         }
      }

}
