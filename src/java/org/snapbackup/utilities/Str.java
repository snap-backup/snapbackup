////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Str.java                                                                   //
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
// String:                                                                    //
//    This object...                                                          //
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
