////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Str.java                                                                   //
//                                                                            //
// String:                                                                    //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

import org.snapbackup.settings.SystemAttributes;

public abstract class Str {

   static final String macroChar = SystemAttributes.replacementChar;  //"%"

   public static String macroExpand(String baseStr, String replaceStr) {
      return baseStr.substring(0, baseStr.indexOf(macroChar)) + replaceStr +
         baseStr.substring(baseStr.lastIndexOf(macroChar) + 1);
      }

   public static String macroExpand(String baseStr, int replaceNum) {
      return macroExpand(baseStr, Integer.toString(replaceNum));
      }

   public static String pad(String str) {
      return " " + str + " ";
      }

   public static String quote(String str) {
      return "\"" + str + "\"";
      }

}
