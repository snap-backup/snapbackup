////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// BackupProgressDialog.java                                                  //
//                                                                            //
// Backup Progress Dialog:                                                    //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
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
   Locale locale = new Locale.Builder().setLanguage(UserPreferences.readLocalePref()).build();
   NumberFormat nfp = NumberFormat.getPercentInstance(locale);
   JLabel progressLabel;
   JProgressBar backupPB;
   JLabel memFreeLabel;
   JButton cancelButton;

   public BackupProgressDialog(ZipEngine zipInst) {
      super(SnapBackupFrame.current);
      current = this;  //todo: AssignmentToNonFinalStatic
      zip = zipInst;
      setTitle(BackupProgressUIProperties.backupProgressTitle);
      nfp.setMinimumFractionDigits(1);
      nfp.setMaximumFractionDigits(1);

      // Create Controls
      progressLabel = new JLabel(BackupProgressUIProperties.backupProgressTag);
      backupPB = new JProgressBar(0, progressMax);
      memFreeLabel = new JLabel(BackupProgressUIProperties.backupProgressMemoryFree);
      cancelButton = new JButton(BackupProgressUIProperties.backupProgressCancel);
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { cancelButtonAction(e); }
         } );

      // Allign Controls
      progressLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 2, 0));
      backupPB.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
      memFreeLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 20, 0));
      progressLabel.setAlignmentX(CENTER_ALIGNMENT);
      backupPB.setAlignmentX(CENTER_ALIGNMENT);
      memFreeLabel.setAlignmentX(CENTER_ALIGNMENT);
      cancelButton.setAlignmentX(CENTER_ALIGNMENT);

      // Add Controls and Display
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
