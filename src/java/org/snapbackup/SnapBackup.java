////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// SnapBackup.java                                                            //
//                                                                            //
// Main Startup:                                                              //
//    This object launches the command line or GUI version of appliction.     //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.snapbackup.business.DataModel;
import org.snapbackup.ui.Application;
import org.snapbackup.settings.UserPreferences;

public abstract class SnapBackup {

   // Run Snap Backup in either command line mode where the parameter is the profile name
   // or in GUI (Swing) mode if there are no parameters.
   public static void main(String[] args) {
      String platformLaf = UIManager.getSystemLookAndFeelClassName();
      if (args.length == 0) {
         try {
            UIManager.setLookAndFeel(platformLaf);
            }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), platformLaf, JOptionPane.ERROR_MESSAGE);
            }
         new Application();
         }
      else if ("--list".equalsIgnoreCase(args[0]))
         DataModel.doCmdLineList();
      else if ("--current".equalsIgnoreCase(args[0]))
         DataModel.doCmdLineBackup(UserPreferences.readPref("CurrentProfile"));
      else if ("~".equals(args[0]))                                              //DEPRECATED
         DataModel.doCmdLineBackup(UserPreferences.readPref("CurrentProfile"));  //DEPRECATED
      else
         DataModel.doCmdLineBackup(args[0]);
      }

}
