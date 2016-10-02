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

public class Str {

   static final String macroChar = "%";  //TextReplacementCharacter

   public static String macroExpand(String baseStr, String replaceStr) {
      return baseStr.substring(0, baseStr.indexOf(macroChar)) + replaceStr +
         baseStr.substring(baseStr.lastIndexOf(macroChar) + 1);
      }

   public static String macroExpand(String baseStr, int replaceNum) {
      return macroExpand(baseStr, Integer.toString(replaceNum));
      }

}
