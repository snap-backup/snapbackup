////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ImportSettingsDialog.java                                                  //
//                                                                            //
// Import Settings Dialog:                                                    //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.prefimport;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import org.snapbackup.business.ExportPrefs;
import org.snapbackup.business.ImportPrefs;
import org.snapbackup.ui.Application;
import org.snapbackup.ui.Icons;
import org.snapbackup.ui.UIUtilities;
import org.snapbackup.ui.SnapBackupFrame;
import org.snapbackup.ui.prefexport.Export;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;

public class ImportDialog extends JDialog {

   // Define Controls
   ImportUIProperties ui = new ImportUIProperties();
   JPanel       basePanel =               new JPanel();
   JPanel       locationPanel =           new JPanel();
   JPanel       locationInnerPanel =      new JPanel();
   JLabel       locationPromptLabel =     new JLabel(ui.locationPrompt + SystemAttributes.space);
   JTextField   locationTextField =       new JTextField(Export.fileNameCols);
   JButton      locationChooserButton =   new JButton(Icons.folderIcon);
   JLabel       locationDetailsLabel =    new JLabel(ui.locationDetails);
   JLabel       locationWarningLabel =    new JLabel(ui.locationWarning);
   JPanel       buttonPanel =             new JPanel();
   JButton      cancelButton =            new JButton(ui.buttonCancel);
   JButton      actionButton =            new JButton(ui.buttonImport);
   JButton[]    buttonList =              { cancelButton, actionButton };

   public ImportDialog(Frame owner) {
      super(owner);
      AppProperties.addSupplimentalProperty(Export.prefSettingsFileName,
         new Export().defaultSettingsFileName);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setTitle(ui.title);
      configureContols();
      addContols();
      getContentPane().add(basePanel);
      setModal(true);
      setResizable(false);
      pack();
      setLocationRelativeTo(owner);
      setVisible(true);
      }

   void configureContols() {
      basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.PAGE_AXIS));
      basePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.PAGE_AXIS));
      locationPanel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createTitledBorder(ui.locationTitle),
         BorderFactory.createEmptyBorder(0, 5, 5, 5)));
      locationInnerPanel.setLayout(new BoxLayout(locationInnerPanel, BoxLayout.LINE_AXIS));
      locationInnerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      locationPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
      locationTextField.setText(UserPreferences.readPref(Export.prefSettingsFileName));
      locationChooserButton.setToolTipText(ui.locationCmd);
      locationChooserButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { locationChooserButtonAction(e); }
         } );
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
      buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionCancel(); } } );
      actionButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionImport(); } } );
      UIUtilities.addFastKeys(buttonList);
      UIUtilities.makeBold(actionButton);
      getRootPane().setDefaultButton(actionButton);
      }

   void addContols() {
      basePanel.add(locationPanel);
      locationPanel.add(locationInnerPanel);
      locationInnerPanel.add(locationPromptLabel);
      locationInnerPanel.add(locationTextField);
      locationInnerPanel.add(Box.createRigidArea(new Dimension(5, 0)));
      locationInnerPanel.add(locationChooserButton);
      locationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      locationPanel.add(locationDetailsLabel);
      locationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      locationPanel.add(locationWarningLabel);
      basePanel.add(Box.createRigidArea(new Dimension(0, 10)));
      basePanel.add(buttonPanel);
      buttonPanel.add(Box.createHorizontalGlue());
      buttonPanel.add(cancelButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
      buttonPanel.add(actionButton);
      }

   //
   // Callback Methods (Event Actions)
   //
   void locationChooserButtonAction(ActionEvent e) {
      JFileChooser locationFileChooser = new JFileChooser();
      locationFileChooser.setSelectedFile(new File(locationTextField.getText()));
      locationFileChooser.setFileFilter(new ExportPrefs().xmlFilter);
      int returnStatus = locationFileChooser.showOpenDialog(this);
      if (returnStatus == JFileChooser.APPROVE_OPTION)
         locationTextField.setText(locationFileChooser.getSelectedFile().getAbsolutePath());
      }

   void actionCancel() {
      this.dispose();
      }

   void actionImport() {
      String errMsg = new ImportPrefs().doImport(locationTextField.getText());
      if (errMsg == null) {
         JOptionPane.showMessageDialog(this, ui.msgSuccess, new Export().Settings,
            JOptionPane.PLAIN_MESSAGE);
         SnapBackupFrame oldFrame = SnapBackupFrame.current;
         AppProperties.reload();
         new Application();
         oldFrame.setEnabled(false);
         oldFrame.dispose();
         this.dispose();
         }
      else
         JOptionPane.showMessageDialog(this, errMsg, ui.locationCmd,
            JOptionPane.ERROR_MESSAGE);
      }

}
