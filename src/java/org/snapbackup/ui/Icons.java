////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// Icons.java                                                                 //
//                                                                            //
// Icons:                                                                     //
//    This object holds icons for display in the UI.                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import java.io.File;
import javax.swing.*;
import org.snapbackup.settings.SystemAttributes;

public class Icons {

   //static final Class iconClass = new Icons().getClass();
   //static final Class iconClass = Icons.class;
   public static final ImageIcon snapBackupIcon = new ImageIcon(Icons.class.getResource(UIProperties.snapBackupIconURL));
   public static final ImageIcon logoIcon = new ImageIcon(Icons.class.getResource(UIProperties.logoIconURL));
   static final JFileChooser sysAccess =  new JFileChooser();
   static final File[] rootDir =  File.listRoots();
   public static final Icon driveIcon =  sysAccess.getIcon(rootDir[0]);
   public static final Icon zipIcon = driveIcon;
      //sysAccess.getIcon(new File(iconClass.getResource('images/logo.png').getPath()));
      //FileSys.makeEmptyFile(UIProperties.zipIconFileName + '.html'));
   public static final Icon folderIcon =
      //sysAccess.getIcon(new File(ProgramAttributes.appWorkingDir));  //bad on macs
      //sysAccess.getIcon(new File(ProgramAttributes.userHomeDir));  //works on win
      sysAccess.getIcon(new File(SystemAttributes.javaHomeDir));  //works on win

   public static ImageIcon langIcon(String localeCode) {
      return new ImageIcon(Icons.class.getResource(
         UIProperties.langIconURLpre + localeCode + UIProperties.langIconURLpost));
      }

}
