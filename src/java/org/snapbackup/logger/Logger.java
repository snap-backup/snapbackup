////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Logger.java                                                                //
//                                                                            //
// Logger:                                                                    //
//    This object is the text area on the UI used to display messages to the  //
//    user while the application is running.                                  //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.logger;

import javax.swing.*;

import org.snapbackup.settings.SystemAttributes;

public abstract class Logger {

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
