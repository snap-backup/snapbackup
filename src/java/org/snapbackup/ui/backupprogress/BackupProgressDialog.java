////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// BackupProgressDialog.java                                                  //
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
// Backup Progress Dialog:                                                    //
//    This object...                                                          //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.backupprogress;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import org.snapbackup.settings.UserPreferences;
import org.snapbackup.ui.SnapBackupFrame;
import org.snapbackup.utilities.ZipEngine;

public class BackupProgressDialog extends JDialog {

   public static BackupProgressDialog current;
   ZipEngine zip;
   final int progressMax = BackupProgressUIProperties.progressMax;
   NumberFormat nfp = NumberFormat.getPercentInstance(new Locale(UserPreferences.readLocalePref()));
   JLabel progressLabel;
   JProgressBar backupPB;
   JLabel memFreeLabel;
   JButton cancelButton;

   public BackupProgressDialog(ZipEngine zipInst) {
      super(SnapBackupFrame.current);
      current = this;
      zip = zipInst;
      setTitle(BackupProgressUIProperties.backupProgressTitle);
      nfp.setMinimumFractionDigits(1);
      nfp.setMaximumFractionDigits(1);

      //Create Controls
      progressLabel = new JLabel(BackupProgressUIProperties.backupProgressTag);
      backupPB = new JProgressBar(0, progressMax);
      memFreeLabel = new JLabel(BackupProgressUIProperties.backupProgressMemoryFree);
      cancelButton = new JButton(BackupProgressUIProperties.backupProgressCancel);
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { cancelButtonAction(e); }
         } );

      //Allign Controls
      progressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 2, 0));
      backupPB.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
      memFreeLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 20, 0));
      progressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      backupPB.setAlignmentX(Component.CENTER_ALIGNMENT);
      memFreeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);

      //Add Controls and Display
      JPanel backupProgressPanel = new JPanel();
      backupProgressPanel.setLayout(new BoxLayout(backupProgressPanel, BoxLayout.Y_AXIS));
      backupProgressPanel.add(progressLabel);
      backupProgressPanel.add(backupPB);
      backupProgressPanel.add(memFreeLabel);
      backupProgressPanel.add(cancelButton);
      backupProgressPanel.add(Box.createRigidArea(new Dimension(0, 20)));
      getContentPane().add(backupProgressPanel);
      setResizable(false);
      setModal(true);
      pack();
      }

   public void updateProgress(int count) {
      backupPB.setValue(count - (count / progressMax) * progressMax);
      progressLabel.setText(BackupProgressUIProperties.backupProgressTag +
         count + BackupProgressUIProperties.backupProgressFiles);
      memFreeLabel.setText(BackupProgressUIProperties.backupProgressMemoryFree +
         nfp.format(1.0 - 1.0*Runtime.getRuntime().totalMemory()/Runtime.getRuntime().maxMemory()));
      }

   public void done() {
      progressLabel.setText(BackupProgressUIProperties.backupProgressTag +
         BackupProgressUIProperties.backupProgressDone);
      }

   public void cancelButtonAction(ActionEvent e) {
      progressLabel.setText(BackupProgressUIProperties.backupProgressTag +
         BackupProgressUIProperties.backupProgressAborting);
      zip.abortBackup();
      }

}
