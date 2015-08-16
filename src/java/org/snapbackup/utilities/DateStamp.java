////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// DateStamp.java                                                             //
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
// Date Stamp:                                                                //
//    This object...                                                          //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

import java.util.Calendar;
import org.snapbackup.ui.options.Options;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;

public class DateStamp {

   static String twoDigitString(int number) {
      String tds = "" + number;
      if (number < 10) tds = "0" + tds;
      return tds;
      //return (number < 10 ? "0" : "") + number;
      }

   public static String todaysDateStamp() {
      //Return today's date stamp in the format: "YYYY-MM-DD"
      //Calendar today = Calendar.getInstance();
      //return "" + today.get(Calendar.YEAR) + "-" +
      //   twoDigitString(today.get(Calendar.MONTH) + 1) + "-" +
      //   twoDigitString(today.get(Calendar.DAY_OF_MONTH));
      return todaysDateStamp(
         UserPreferences.readPref(Options.prefOrder),
         UserPreferences.readPref(Options.prefYear).equals(Options.year2Digits),
         UserPreferences.readPref(Options.prefSeparator));
      }

   public static String todaysDateStamp(String order, boolean year2Digits, String separator) {
      //Return today's date stamp
      Calendar today = Calendar.getInstance();
      String year = Integer.toString(today.get(Calendar.YEAR));
      if (year2Digits)
         year = year.substring(2);
      String month = twoDigitString(today.get(Calendar.MONTH) + 1);
      String day = twoDigitString(today.get(Calendar.DAY_OF_MONTH));
      String dateStamp = SystemAttributes.nullStr;
      for (char ymd : order.toCharArray())
         switch (ymd) {
            case '0': dateStamp = dateStamp + separator + year;   break;
            case '1': dateStamp = dateStamp + separator + month;  break;
            case '2': dateStamp = dateStamp + separator + day;    break;
            }
      /*
      for (int slot = 0; slot < order.length(); slot++)
         switch (order.charAt(slot)) {
            case '0': dateStamp = dateStamp + separator + year;   break;
            case '1': dateStamp = dateStamp + separator + month;  break;
            case '2': dateStamp = dateStamp + separator + day;    break;
            }
      */
      return dateStamp.substring(1);
      }

}
