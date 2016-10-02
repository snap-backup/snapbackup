////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// CheckForUpdates.java                                                       //
//                                                                            //
// Data Model:                                                                //
//    This object is the business logic of MVC.                               //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.snapbackup.logger.Logger;
import org.snapbackup.settings.SystemAttributes;

public class CheckForUpdates {
   final static String verificationCheck = "Snap Backup";

   public static String getLatestVersion() {
      String version = null;
      String verification = null;
      try {
         BufferedReader reader = new BufferedReader(
            new InputStreamReader(new URL(SystemAttributes.updatesURL).openStream()));
         version = reader.readLine();
         verification = reader.readLine();
         reader.close();
         }
      catch (Exception e) { //NoRouteToHostException or ConnectException
         Logger.logMsg(e.toString());
         }
      return verificationCheck.equals(verification) ? version : null;
      }

}
