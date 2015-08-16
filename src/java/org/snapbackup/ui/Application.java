////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Application.java                                                           //
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
// Application:                                                               //
//    This object controls the flow of actions necessary to start up Snap     //
//    Backup.                                                                 //
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
