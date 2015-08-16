////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// CheckForUpdates.java                                                       //
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
// Data Model:                                                                //
//    This object is the business logic of MVC.                               //
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
