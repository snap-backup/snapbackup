////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Logger.java                                                                //
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
// Snap Backup is a registered trademark of Center Key Software               //
// http://snapbackup.org                                                      //
//                                                                            //
// Logger:                                                                    //
//    This object is the text area on the UI used to display messages to the  //
//    user while the application is running.                                  //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.logger;

import javax.swing.*;

import org.snapbackup.settings.SystemAttributes;

public class Logger {

   static JTextArea logArea;
   static boolean log2Screen = true;
   static boolean firstSession = true;

   public static void initLogArea(JTextArea area) {
      log2Screen = true;
      logArea = area;
      }

   public static void initOutput() {
      log2Screen = false;
      }

   public static void logMsg(String msg) {
      if (log2Screen) {
         logArea.append(SystemAttributes.newLine);
         logArea.append(msg);
         logArea.setCaretPosition(logArea.getText().length());
         }
      else
         System.out.println(msg);
      }

   /* //The better way to do this?????
   //You must not update the textArea from your worker thread, instead use:
   public static void logMsg(String msg) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            logTextArea.append(newLine);
            logTextArea.append(msg);
            }
         } );
      }
   */

   public static void spacer() {
      if (!firstSession) logMsg("");
      firstSession = false;
      }
   
   }

