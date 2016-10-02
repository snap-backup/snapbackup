////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Application.java                                                           //
//                                                                            //
// Application:                                                               //
//    This object controls the flow of actions necessary to start up Snap     //
//    Backup.                                                                 //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import org.snapbackup.business.DataModel;
import org.snapbackup.logger.Logger;

public final class Application {

   public Application() {
      new UIProperties();
      //SplashScreen splash = new SplashScreen();
      //UIUtilities.centerDialog(splash);
      SnapBackupFrame frame = new SnapBackupFrame();
      Logger.initLogArea(frame.getLogTextArea());
      DataModel.initSettings(frame);
      UIUtilities.lockInMinSize(frame);
      UIUtilities.setInitialNumRows(frame);
      UIUtilities.centerFrame(frame);
      //splash.delete();
      }

}
