////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Main.java                                                                  //
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
// Main Startup:                                                              //
//    This object launches the command line or GUI version of appliction.     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.snapbackup.business.DataModel;
import org.snapbackup.ui.Application;
import org.snapbackup.ui.UIProperties;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.UserPreferences;

public class Main {

   // Run Snap Backup in either command line mode where the parameter is the profile name
   // or in GUI (Swing) mode if there are no parameters.
   public static void main(String[] argv) {
      if (argv.length > 0)
         DataModel.doCmdLineBackup(argv[0], argv.length > 1 ? argv[1] : null);
      else {
         AppProperties.addSupplimentalProperty(DataModel.prefSkinName,
            UIManager.getSystemLookAndFeelClassName());  //make system l&f the default
         try {
            UIManager.setLookAndFeel(UserPreferences.readPref(DataModel.prefSkinName));
            }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
               UIProperties.skinErrMsg, JOptionPane.ERROR_MESSAGE);
            UserPreferences.deletePref(DataModel.prefSkinName);
            }
         UserPreferences.migrateOldSettings();  //pre v5.4 --> v5.4
         new Application();
         }
      }

}
