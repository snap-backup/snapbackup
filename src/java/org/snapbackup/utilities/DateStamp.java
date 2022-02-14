////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// DateStamp.java                                                             //
//                                                                            //
// Date Stamp:                                                                //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

import java.util.Calendar;
import org.snapbackup.ui.options.Options;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;

public abstract class DateStamp {

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
