////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// FilterDialog.java                                                          //
//                                                                            //
// Filter Dialog:                                                             //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.filter;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import org.snapbackup.business.DataModel;
import org.snapbackup.ui.SnapBackupFrame;
import org.snapbackup.ui.UIUtilities;
import org.snapbackup.settings.SystemAttributes;

public class FilterDialog extends JDialog {

   SnapBackupFrame    f =       SnapBackupFrame.current;
   String             nullStr = SystemAttributes.nullStr;
   FilterUIProperties ui =      new FilterUIProperties();

   // Define Controls
   JPanel     filterPanel =               new JPanel();

   JLabel     tagLabel =                  new JLabel(ui.filterRuleTag);
   JLabel     backupItemLabel =           new JLabel(DataModel.getCurrentZipItem(f));

   JPanel     includePanel =              new JPanel();
   JPanel     includeDataPanel =          new JPanel();
   JLabel     includePromptLabel =        new JLabel(ui.filterRuleIncludePrompt);
   JTextField includeFilterField =        new JTextField(DataModel.getCurrentZipIncludeFilter(f));
   JLabel     includeHelpLabel =          new JLabel(ui.filterRuleIncludeHelp);

   JPanel     excludePanel =              new JPanel();
   JPanel     excludeDataPanel =          new JPanel();
   JLabel     excludePromptLabel =        new JLabel(ui.filterRuleExcludePrompt);
   JTextField excludeFilterField =        new JTextField(DataModel.getCurrentZipExcludeFilter(f));
   JLabel     excludeHelpLabel =          new JLabel(ui.filterRuleExcludeHelp);
   JPanel     excludeFoldersPanel =       new JPanel();
   JLabel     excludeFoldersPromptLabel = new JLabel(ui.filterRuleExcludeFoldersPrompt);
   JTextField excludeFoldersFilterField = new JTextField(DataModel.getCurrentZipExcludeFolderFilter(f));
   JLabel     excludeFoldersHelpLabel =   new JLabel(ui.filterRuleExcludeFoldersHelp);
   JPanel     excludeSizePanel =          new JPanel();
   JLabel     excludeSizePromptLabel =    new JLabel(ui.filterRuleExcludeSizePrompt);
   JTextField excludeSizeField =          new JTextField(DataModel.getCurrentZipExcludeSizeFilter(f));
   JLabel     excludeSizeUnitsLabel =     new JLabel(ui.filterRuleExcludeSizeUnits);

   JLabel     overrideNoteLabel =         new JLabel(ui.filterRuleNote);

   JPanel     buttonPanel =               new JPanel();
   JButton    deleteButton =              new JButton(ui.filterRuleButtonDelete);
   JButton    cancelButton =              new JButton(ui.filterRuleButtonCancel);
   JButton    okButton =                  new JButton(ui.filterRuleButtonOk);
   JButton[]  buttonList =                { deleteButton, cancelButton, okButton };

   public FilterDialog(Frame owner) {
      super(owner);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      setTitle(ui.filterRuleTitle);
      configureContols();
      addContols();
      setModal(true);
      setResizable(false);
      pack();
      excludeSizeField.setSize(80, excludeSizeField.getSize().height);
      excludeSizeField.setMaximumSize(excludeSizeField.getSize());
      setLocationRelativeTo(owner);
      setVisible(true);
      }

   void configureContols() {
      filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS));
      filterPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
      UIUtilities.makeBold(backupItemLabel);
      includePanel.setLayout(new BoxLayout(includePanel, BoxLayout.PAGE_AXIS));
      includePanel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createTitledBorder(ui.filterRuleIncludeTitle),
         BorderFactory.createEmptyBorder(0, 5, 5, 5)));
      includeDataPanel.setLayout(new BoxLayout(includeDataPanel, BoxLayout.LINE_AXIS));
      includeDataPanel.setAlignmentX(LEFT_ALIGNMENT);  //Why???  Swing deficiency
      excludePanel.setLayout(new BoxLayout(excludePanel, BoxLayout.PAGE_AXIS));
      excludePanel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createTitledBorder(ui.filterRuleExcludeTitle),
         BorderFactory.createEmptyBorder(0, 5, 5, 5)));
      excludeDataPanel.setLayout(new BoxLayout(excludeDataPanel, BoxLayout.LINE_AXIS));
      excludeDataPanel.setAlignmentX(LEFT_ALIGNMENT);  //Why???  Swing deficiency
      excludeFoldersPanel.setLayout(new BoxLayout(excludeFoldersPanel, BoxLayout.LINE_AXIS));
      excludeFoldersPanel.setAlignmentX(LEFT_ALIGNMENT);  //Why???  Swing deficiency
      excludeSizePanel.setLayout(new BoxLayout(excludeSizePanel, BoxLayout.LINE_AXIS));
      excludeSizePanel.setAlignmentX(LEFT_ALIGNMENT);  //Why???  Swing deficiency
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
      buttonPanel.setAlignmentX(LEFT_ALIGNMENT);  //Why???  Swing deficiency
      deleteButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionDelete(); } } );
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionCancel(); } } );
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionOk(); } } );
      UIUtilities.addFastKeys(buttonList);
      UIUtilities.makeBold(okButton);
      getRootPane().setDefaultButton(okButton);
      getRootPane().registerKeyboardAction(new ActionListener() {
         public void actionPerformed(ActionEvent e) { actionCancel(); } },
         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
         JComponent.WHEN_IN_FOCUSED_WINDOW);
      }

   void addContols() {
      filterPanel.add(tagLabel);
      filterPanel.add(backupItemLabel);
      filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

      includeDataPanel.add(includePromptLabel);
      includeDataPanel.add(includeFilterField);
      includePanel.add(includeDataPanel);
      includePanel.add(includeHelpLabel);
      filterPanel.add(includePanel);
      filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

      excludeDataPanel.add(excludePromptLabel);
      excludeDataPanel.add(excludeFilterField);
      excludePanel.add(excludeDataPanel);
      excludePanel.add(excludeHelpLabel);
      excludePanel.add(Box.createRigidArea(new Dimension(0, 10)));
      excludeFoldersPanel.add(excludeFoldersPromptLabel);
      excludeFoldersPanel.add(excludeFoldersFilterField);
      excludePanel.add(excludeFoldersPanel);
      excludePanel.add(excludeFoldersHelpLabel);
      excludePanel.add(Box.createRigidArea(new Dimension(0, 10)));
      excludeSizePanel.add(excludeSizePromptLabel);
      excludeSizePanel.add(excludeSizeField);
      excludeSizePanel.add(excludeSizeUnitsLabel);
      excludePanel.add(excludeSizePanel);
      filterPanel.add(excludePanel);
      filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

      filterPanel.add(overrideNoteLabel);
      filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));

      buttonPanel.add(Box.createHorizontalGlue());
      buttonPanel.add(deleteButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
      buttonPanel.add(cancelButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
      buttonPanel.add(okButton);
      filterPanel.add(buttonPanel);
      getContentPane().add(filterPanel);
      }

   //
   // Callback Methods (Event Actions)
   //
   // Buttons
   void actionDelete() {
      DataModel.setCurrentZipFilter(nullStr, nullStr, nullStr, nullStr, f);
      this.dispose();
      }
   void actionCancel() {
      this.dispose();
      }
   void actionOk() {
      cleanupFilterData();
      DataModel.setCurrentZipFilter(includeFilterField.getText(), excludeFilterField.getText(),
         excludeFoldersFilterField.getText(), excludeSizeField.getText(), f);
      this.dispose();
      }

   String stripChar(String s, char c) {
      int loc = s.indexOf(c);
      if (loc == -1)
         return s;
      else
         return stripChar(s.substring(0, loc) + s.substring(loc + 1), c);
      }

   String cleanupFilter(String filter) {
      return stripChar(stripChar(filter.replaceAll("\\s*,\\s*", ", "),
         SystemAttributes.fileSeparator.charAt(0)), '\\');  //remove "\" to protect regex
      }

   void cleanupFilterData() {
      includeFilterField.setText(cleanupFilter(includeFilterField.getText()));
      excludeFilterField.setText(cleanupFilter(excludeFilterField.getText()));
      excludeFoldersFilterField.setText(cleanupFilter(excludeFoldersFilterField.getText()));
      excludeSizeField.setText(excludeSizeField.getText().replaceAll("[^0-9]", nullStr));
      excludeSizeField.setText(excludeSizeField.getText().replaceAll("^0*", nullStr));
      if (excludeSizeField.getText().length() > 5)
          excludeSizeField.setText("100000");
      }

}
