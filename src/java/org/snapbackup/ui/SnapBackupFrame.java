////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// SnapBackupFrame.java                                                       //
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
// Snap Backup Frame:                                                         //
//    This object is the base UI frame and UI widgets for the main            //
//    application window.                                                     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.snapbackup.business.DataModel;
import org.snapbackup.business.CheckForUpdates;
import org.snapbackup.ui.about.AboutDialog;
import org.snapbackup.ui.filter.FilterDialog;
import org.snapbackup.ui.prefexport.ExportDialog;
import org.snapbackup.ui.prefimport.ImportDialog;
import org.snapbackup.ui.options.Options;
import org.snapbackup.ui.options.OptionsDialog;
import org.snapbackup.ui.userguide.UserGuideDialog;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;
import org.snapbackup.utilities.Browser;
import org.snapbackup.utilities.Str;

public class SnapBackupFrame extends JFrame {

   UIProperties ui = UIProperties.current;

   //Define Menu Controls
   JMenuBar    menuBar =           new JMenuBar();
   JMenu       fileMenuGroup =     new JMenu(ui.menuGroupFile);
   JMenu       languagesMenuItem = new JMenu(ui.menuItemLangs);
   JRadioButtonMenuItem languagesMenuItemButtonShow =
                                   new JRadioButtonMenuItem(ui.menuItemLangsShow);
   JRadioButtonMenuItem languagesMenuItemButtonHide =
                                   new JRadioButtonMenuItem(ui.menuItemLangsHide);
   ButtonGroup languagesGroup =    new ButtonGroup();
   JMenu       filtersMenuItem =   new JMenu(ui.menuItemFilters);
   JRadioButtonMenuItem filtersMenuItemButtonOn =
                                   new JRadioButtonMenuItem(ui.menuItemFiltersOn);
   JRadioButtonMenuItem filtersMenuItemButtonOff =
                                   new JRadioButtonMenuItem(ui.menuItemFiltersOff);
   ButtonGroup filtersGroup =      new ButtonGroup();
   JMenu       profilesMenuItem =  new JMenu(ui.menuItemProfiles);
   JRadioButtonMenuItem profilesMenuItemButtonOn =
                                   new JRadioButtonMenuItem(ui.menuItemProfilesOn);
   JRadioButtonMenuItem profilesMenuItemButtonOff =
                                   new JRadioButtonMenuItem(ui.menuItemProfilesOff);
   ButtonGroup profilesGroup =     new ButtonGroup();
   JMenu       skinMenuItem =      new JMenu(ui.menuItemSkin);
   JMenuItem   exportMenuItem =    new JMenuItem(ui.menuItemExport);
   JMenuItem   importMenuItem =    new JMenuItem(ui.menuItemImport);
   JMenuItem   optionsMenuItem =   new JMenuItem(ui.menuItemOptions);
   JMenuItem   exitMenuItem =      new JMenuItem(ui.menuItemExit);
   JMenu       helpMenuGroup =     new JMenu(ui.menuGroupHelp);
   JMenuItem   guideMenuItem =     new JMenuItem(ui.menuItemGuide);
   JMenuItem   updatesMenuItem =   new JMenuItem(ui.menuItemUpdates);
   JMenuItem   aboutMenuItem =     new JMenuItem(ui.menuItemAbout);

   //Define Base
   JPanel  basePanel =          new JPanel();

   //Define Header Panels
   JPanel   headerPanel =     new JPanel();
   JPanel   titlePanel =      new JPanel();  //holds title & source
   JLabel   titleLabel =      new JLabel(ui.header);
   int      maxFlagsPerRows = 6;
   int      numFlagRows =     (SystemAttributes.localeCodes.length - 1) / maxFlagsPerRows + 1;
   int      flagsPerRow =     (SystemAttributes.localeCodes.length - 1) / numFlagRows + 1;
   JPanel[] langFlagsPanels = new JPanel[numFlagRows];
   JPanel   iconPanel =       new JPanel();  //holds icon & profiles
   JLabel   iconLabel =       new JLabel(Icons.snapBackupIcon);

   //Define Profiles Controls
   JPanel     profilesPanel =        new JPanel();
   JPanel     profilesInnerPanel =   new JPanel();
   JPanel     profilesButtonPanel =  new JPanel();
   JLabel     profilesPromptLabel =  new JLabel(ui.profilesPrompt);
   JTextField profilesCurrentTextField = new JTextField();  //hidden field
   JComboBox<String> profilesDropDown =  new JComboBox<String>(DataModel.getProfilesNames());  //note: holds user data
   JButton    profilesAddButton =    new JButton(ui.profilesNew);
   JButton    profilesDeleteButton = new JButton(ui.profilesDelete);

   //Define Source (Zip List) Controls
   JPanel       srcPanel =             new JPanel();
   JPanel       srcPanelButtons =      new JPanel();
   JLabel       srcPromptLabel =       new JLabel(ui.srcPrompt);
   final DefaultListModel<String> srcZipListModel = new DefaultListModel<String>();
   JList<String> srcZipList =          new JList<String>(srcZipListModel);
   JScrollPane  srcZipListScrollPane = new JScrollPane();
   JButton      srcAddFileButton =     new JButton(ui.srcAddFile);
   JButton      srcAddFolderButton =   new JButton(ui.srcAddFolder);
   JButton      srcRemoveButton =      new JButton(ui.srcRemove);
   JButton      srcFilterButton =      new JButton(ui.srcFilter);

   //Define Tip
   JLabel tipLabel = new JLabel(ui.tip);

   //Define Destination (Backup & Archive) Controls
   JPanel       destPanel =                 new JPanel(new GridBagLayout());
   JLabel       destBackupPromptLabel =     new JLabel(ui.destBackupPrompt);
   JTextField   destBackupDirTextField =    new JTextField(UIProperties.srcDirCols);
   JButton      destBackupChooserButton =   new JButton(Icons.folderIcon);
   JLabel       destBackupNamePromptLabel = new JLabel(ui.destBackupNamePrompt);
   JTextField   destBackupNameTextField =   new JTextField(UIProperties.srcNameCols);
   JLabel       destBackupTagLabel =        new JLabel(ui.destBackupTag);
   JLabel       destBackupPathLabel =       new JLabel();
   JCheckBox    destArchivePromptCheckBox = new JCheckBox(ui.destArchivePrompt);
   JTextField   destArchiveDirTextField =   new JTextField(UIProperties.archiveDirCols);
   JButton      destArchiveChooserButton =  new JButton(Icons.folderIcon);
   JLabel       destArchiveTagLabel =       new JLabel(ui.destArchiveTag);
   JLabel       destArchivePathLabel =      new JLabel();

   //Define Log Controls
   JPanel      logPanel =      new JPanel();
   JScrollPane logScrollPane = new JScrollPane();
   JTextArea   logTextArea =   new JTextArea(UIProperties.logMinRows, UIProperties.logCols);

   //Define Button Controls
   JPanel  buttonPanel =    new JPanel();
   JButton saveButton =     new JButton(ui.buttonSave);
   JButton resetButton =    new JButton(ui.buttonReset);
   JButton doBackupButton = new JButton(ui.buttonDoBackup);
   JButton exitButton =     new JButton(ui.buttonExit);

   JButton[] buttonList = { srcAddFileButton, srcAddFolderButton, srcRemoveButton, srcFilterButton,
         profilesAddButton, profilesDeleteButton, saveButton, resetButton, doBackupButton, exitButton };

   public static SnapBackupFrame current;

   public SnapBackupFrame() {
      current = this;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //setTitle(UIProperties.appTitle);
      setTitle(ui.appTitle);
      configureContols();
      addContols();
      setupMenuCallbacks();
      UIUtilities.addFastKeys(menuBar);
      setupCallbacks();
      pack();
      destPanel.setMaximumSize(new Dimension(100000, destPanel.getSize().height));
      doBackupButton.grabFocus();
      }

   void popupMsg(String msg, String title) {
      JOptionPane.showMessageDialog(this, msg, title, JOptionPane.PLAIN_MESSAGE);
      }

   void popupMsg(String msg) {
      popupMsg(msg, ui.appTitle);
      }

   class LocaleCodeComparator implements Comparator<String> {
      Locale locale;
      LocaleCodeComparator(Locale currentLocale) {
         locale = currentLocale;
         }
      public int compare(String localeCodeA, String localeCodeB) {
         Locale a = new Locale(localeCodeA);
         Locale b = new Locale(localeCodeB);
         return
            a.equals(b) ? 0 :
            a.equals(locale) ? -1 :
            b.equals(locale) ? 1 :
            a.getDisplayName(locale).compareTo(b.getDisplayName(locale));
         }
      }

   void configureContols() {
      //Configure Menu Controls
      languagesGroup.add(languagesMenuItemButtonShow);
      languagesGroup.add(languagesMenuItemButtonHide);
      languagesMenuItem.add(languagesMenuItemButtonShow);  //menu: File | Language Options | Show
      languagesMenuItem.add(languagesMenuItemButtonHide);  //menu: File | Language Options | Hide
      languagesMenuItem.setMnemonic('C');
      filtersGroup.add(filtersMenuItemButtonOn);
      filtersGroup.add(filtersMenuItemButtonOff);
      filtersMenuItem.add(filtersMenuItemButtonOn);   //menu: File | Backup Filters | On
      filtersMenuItem.add(filtersMenuItemButtonOff);  //menu: File | Backup Filters | Off
      profilesGroup.add(profilesMenuItemButtonOn);
      profilesGroup.add(profilesMenuItemButtonOff);
      profilesMenuItem.add(profilesMenuItemButtonOn);   //menu: File | Multiple Profiles | On
      profilesMenuItem.add(profilesMenuItemButtonOff);  //menu: File | Multiple Profiles | Off

      //Configure Base
      basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.PAGE_AXIS));
      basePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

      //Configure Header
      headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
      titlePanel.setLayout(new BorderLayout());
      iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.PAGE_AXIS));
      tipLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
      titleLabel.setHorizontalAlignment(JLabel.CENTER);
      for (int count = 0; count < numFlagRows; count++)
         langFlagsPanels[count] = new JPanel();
      for (JPanel panel : langFlagsPanels) {
         panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
         panel.setAlignmentX(1.0f);
         }
      iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));   //????
      iconLabel.setAlignmentX(1.0f);
      titleLabel.setAlignmentX(0.5f);
      srcPanel.setAlignmentX(0.5f);
      tipLabel.setAlignmentX(0.5f);

      //Configure Profiles
      profilesPanel.setLayout(new FlowLayout());
      profilesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
      profilesPanel.setAlignmentX(1.0f);
      profilesInnerPanel.setBorder(BorderFactory.createTitledBorder(ui.profilesTitle));
      profilesInnerPanel.setLayout(new BoxLayout(profilesInnerPanel, BoxLayout.PAGE_AXIS));
      profilesPromptLabel.setAlignmentX(0.0f);
      profilesDropDown.setAlignmentX(0.0f);
      profilesButtonPanel.setAlignmentX(0.0f);

      //Configure Source (Zip List) Controls
      srcPanel.setLayout(new BorderLayout());
      srcPanel.setBorder(BorderFactory.createTitledBorder(ui.srcTitle));
      srcZipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      srcZipList.setVisibleRowCount(UIProperties.srcMinRows);  //Set field height
      //srcZipList.setVisibleRowCount(15);  //Set field height !!!!!!!!!!

      //Configure Tip
      tipLabel.setHorizontalAlignment(JLabel.CENTER);
      tipLabel.setToolTipText(ui.tipHelp);
      tipLabel.setFont(new Font(null, Font.BOLD, tipLabel.getFont().getSize()-1));

      //Configure Destination (Backup & Archive) Controls
      destPanel.setBorder(BorderFactory.createTitledBorder(ui.destTitle));
      //destBackupChooserButton.setIcon(Icons.folderIcon);
      destBackupChooserButton.setToolTipText(ui.destBackupCmd);
      UIUtilities.makeEmphasized(destBackupPathLabel);
      destBackupTagLabel.setIcon(Icons.zipIcon);
      destArchivePromptCheckBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      //destArchiveChooserButton.setIcon(Icons.folderIcon);
      destArchiveChooserButton.setToolTipText(ui.destArchiveCmd);
      destArchiveTagLabel.setIcon(Icons.driveIcon);
      UIUtilities.makeEmphasized(destArchivePathLabel);

      //Configure Log Controls
      logPanel.setLayout(new BorderLayout());
      logPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 0, 5, 0),
            BorderFactory.createTitledBorder(ui.logTitle)));
      logTextArea.setEditable(false);
      logTextArea.setFont(UIProperties.standardFont);
      logTextArea.setBackground(Color.lightGray);
      logTextArea.setBorder(BorderFactory.createLoweredBevelBorder());
      /*   OLD BAD WAY???
      logScrollPane.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createEmptyBorder(10, 0, 10, 0),
         BorderFactory.createTitledBorder(ui.logTitle)));
         */

      //Configure Button Controls
      UIUtilities.addFastKeys(buttonList);
      UIUtilities.makeBold(doBackupButton);
      getRootPane().setDefaultButton(doBackupButton);
      }

   public void addContols() {
      //Add Menu Controls
      setJMenuBar(menuBar);
      menuBar.add(fileMenuGroup);           //menu: File
      fileMenuGroup.add(languagesMenuItem); //menu: File | Langauge Options
      fileMenuGroup.add(filtersMenuItem);   //menu: File | Backup Filters
      fileMenuGroup.add(profilesMenuItem);  //menu: File | Multiple Profiles
      fileMenuGroup.add(skinMenuItem);      //menu: File | Look & Feel
      fileMenuGroup.addSeparator();
      fileMenuGroup.add(exportMenuItem);    //menu: File | Export Settings...
      fileMenuGroup.add(importMenuItem);    //menu: File | Import Settings...
      fileMenuGroup.addSeparator();
      fileMenuGroup.add(optionsMenuItem);   //menu: File | Options...    ###############################
      fileMenuGroup.add(exitMenuItem);      //menu: File | Exit
      menuBar.add(helpMenuGroup);           //menu: Help
      helpMenuGroup.add(guideMenuItem);     //menu: Help | User Guide
      helpMenuGroup.add(updatesMenuItem);   //menu: Help | Check for Updates
      helpMenuGroup.add(aboutMenuItem);     //menu: Help | About

      //Add Title Control
      titlePanel.add(titleLabel, BorderLayout.PAGE_START);

      ////////////////////////
      //Configure Language Flags
      final String localeCodeKey = SystemAttributes.userName;  //actual value not important
      Locale currentLocale = new Locale(UserPreferences.readLocalePref());
      //sortLocaleCodes(SystemAttributes.localeCodes, currentLocale);
      Arrays.sort(SystemAttributes.localeCodes,
         new LocaleCodeComparator(currentLocale));
      for (int count = 0; count < SystemAttributes.localeCodes.length; count++) {
         String localeCode = SystemAttributes.localeCodes[count];
         Locale locale = new Locale(localeCode);
         JLabel langLabel = new JLabel(Icons.langIcon(localeCode));
         langLabel.setToolTipText(locale.getDisplayLanguage(currentLocale) +
            (locale.equals(currentLocale) ? SystemAttributes.nullStr :
            SystemAttributes.dividerStr + locale.getDisplayLanguage(locale)));
         langLabel.putClientProperty(localeCodeKey, localeCode);
         langLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
               selectLanguage((String)((JLabel)e.getSource()).getClientProperty(
                  localeCodeKey)); }
            public void mouseEntered(MouseEvent e) {
               setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }
            public void mouseExited(MouseEvent e) {
               setCursor(Cursor.getPredefinedCursor (Cursor.DEFAULT_CURSOR)); }
            public void mousePressed(MouseEvent e)  { /* do nothing*/ }
            public void mouseReleased(MouseEvent e) { /* do nothing*/ }
            } );
         langFlagsPanels[count/flagsPerRow].add(langLabel);
         }
      ////////////////////////

      for (JPanel flagPanel : langFlagsPanels)
         iconPanel.add(flagPanel);
      //for (int count = 0; count < numFlagRows; count++)
      //   iconPanel.add(langFlagsPanels[count]);
      iconPanel.add(iconLabel);

      //Add Profiles
      profilesInnerPanel.add(profilesPromptLabel);
      profilesInnerPanel.add(profilesDropDown);
      profilesButtonPanel.add(profilesAddButton);
      profilesButtonPanel.add(profilesDeleteButton);
      profilesInnerPanel.add(profilesButtonPanel);
      profilesPanel.add(profilesInnerPanel);
      iconPanel.add(profilesPanel);

      //Add Source (Zip List) Controls
      srcZipListScrollPane.setViewportView(srcZipList);
      srcPanel.add(srcPromptLabel, BorderLayout.PAGE_START);
      srcPanel.add(srcZipListScrollPane, BorderLayout.CENTER);
      srcPanelButtons.add(srcAddFileButton);
      srcPanelButtons.add(srcAddFolderButton);
      srcPanelButtons.add(srcRemoveButton);
      srcPanelButtons.add(srcFilterButton);
      srcPanel.add(srcPanelButtons, BorderLayout.PAGE_END);
      titlePanel.add(srcPanel, BorderLayout.CENTER);

      //Add Tip
      titlePanel.add(tipLabel, BorderLayout.PAGE_END);
      headerPanel.add(titlePanel);
      headerPanel.add(iconPanel);
      basePanel.add(headerPanel);

      //Add Destination (Backup and Archive) Controls
      GridBagConstraints destFormat = new GridBagConstraints();
      destFormat.anchor = GridBagConstraints.WEST;
      destFormat.insets = new Insets(0, 10, 0, 10);  //top, left, bottom, right
      destPanel.add(destBackupPromptLabel,     gridLoc(destFormat, 0, 0, 4, 1));
      destPanel.add(destBackupDirTextField,    gridLoc(destFormat, 0, 1, 3, 1,
         GridBagConstraints.HORIZONTAL));
      destPanel.add(destBackupChooserButton,   gridLoc(destFormat, 3, 1));
      destPanel.add(destBackupNamePromptLabel, gridLoc(destFormat, 4, 0));
      destPanel.add(destBackupNameTextField,   gridLoc(destFormat, 4, 1));
      destPanel.add(destBackupTagLabel,        gridLoc(destFormat, 0, 2));
      destPanel.add(destArchivePromptCheckBox, gridLoc(destFormat, 0, 3, 3, 1));
      destPanel.add(destArchiveDirTextField,   gridLoc(destFormat, 0, 4, 2, 1,
         GridBagConstraints.HORIZONTAL));
      destPanel.add(destArchiveChooserButton,  gridLoc(destFormat, 2, 4));
      destPanel.add(destArchiveTagLabel,       gridLoc(destFormat, 0, 5));
      destFormat.insets = new Insets(2, 0, 0, 0);  //top, left, bottom, right
      destPanel.add(destBackupPathLabel,       gridLoc(destFormat, 1, 2, 4, 1));
      destPanel.add(destArchivePathLabel,      gridLoc(destFormat, 1, 5, 4, 1));
      basePanel.add(destPanel);

      //Add Log Controls
      logScrollPane.setViewportView(logTextArea);
      logPanel.add(logScrollPane, BorderLayout.CENTER);
      basePanel.add(logPanel);

      //Add Button Controls
      buttonPanel.add(saveButton);
      buttonPanel.add(resetButton);
      buttonPanel.add(doBackupButton);
      buttonPanel.add(exitButton);
      basePanel.add(buttonPanel);

      //Put It All Together
      getContentPane().add(basePanel);

      }

   //
   // GridBagConstraints Utilities
   //
   public GridBagConstraints gridLoc(GridBagConstraints constraints,
            int col, int row, int colSpan, int rowSpan, int stretch) {
      constraints.gridx = col;
      constraints.gridy = row;
      constraints.gridwidth = colSpan;
      constraints.gridheight = rowSpan;
      constraints.fill = stretch;
      constraints.weightx = (stretch == GridBagConstraints.NONE ? 0.0 : 0.5);
      constraints.weighty = (stretch == GridBagConstraints.BOTH ? 0.5 : 0.0);
      return constraints;
      }
   public GridBagConstraints gridLoc(GridBagConstraints constraints,
            int col, int row, int colSpan, int rowSpan) {
      return gridLoc(constraints, col, row, colSpan, rowSpan,
         GridBagConstraints.NONE);
      }
   public GridBagConstraints gridLoc(GridBagConstraints constraints,
            int col, int row) {
      return gridLoc(constraints, col, row, 1, 1, GridBagConstraints.NONE);
      }

   //
   // Access to UI Controls
   //
   public JComboBox<String> getProfilesDropDown() {
      return profilesDropDown;
      }
   public void initLanguagesUI(boolean showLanguages) {
      languagesMenuItemButtonShow.setSelected(showLanguages);
      languagesMenuItemButtonHide.setSelected(!showLanguages);
      for (JPanel flagPanel: langFlagsPanels)
         flagPanel.setVisible(showLanguages);
      }
   public boolean getShowLanguages() {
      return languagesMenuItemButtonShow.isSelected();
      }
   public void initFiltersUI(boolean filtersEnabled) {
      filtersMenuItemButtonOn.setSelected(filtersEnabled);
      filtersMenuItemButtonOff.setSelected(!filtersEnabled);
      srcFilterButton.setVisible(filtersEnabled);
      }
   public boolean getFiltersEnabled() {
      return filtersMenuItemButtonOn.isSelected();
      }
   public String getSkinName() {
      return (String)skinMenuItem.getSelectedObjects()[0];
      }
   public void initProfilesUI(boolean showProfiles) {
      profilesMenuItemButtonOn.setSelected(showProfiles);
      profilesMenuItemButtonOff.setSelected(!showProfiles);
      profilesInnerPanel.setVisible(showProfiles);
      if (profilesDropDown.getItemCount() == 1)
         profilesDeleteButton.setEnabled(false);
      }
   public boolean getProfilesEnabled() {
      return profilesMenuItemButtonOn.isSelected();
      }
   public void setProfilesDropDown(String[] profileNames) {
      profilesDropDown.removeAllItems();
      for (String profileName : profileNames)
         profilesDropDown.addItem(profileName);
      }
   public String readProfilesDropDown() {
      String profileList = profilesDropDown.getItemAt(0);
      for (int count = 1; count < profilesDropDown.getItemCount(); count++)
         profileList = profileList + SystemAttributes.newLine + profilesDropDown.getItemAt(count);
      return profileList;
      }
   public void setCurrentProfile(String profileName) {
       profilesCurrentTextField.setText(profileName);
       }
   public void selectProfile(String profileName) {
       setCurrentProfile(profileName);
       profilesDropDown.setSelectedItem(profileName);  //triggers callback (unless unchanged)
       }
   public String getCurrentProfileName() {
      //return (String)profilesDropDown.getSelectedItem();
      return profilesCurrentTextField.getText();
      }
   public DefaultListModel<String> getSrcZipListModel() {
      return srcZipListModel;
      }
   public JList<String> getSrcZipList() {
      return srcZipList;
      }
   public JButton getSrcRemoveButton() {
       return srcRemoveButton;
       }
   public JButton getSrcFilterButton() {
       return srcFilterButton;
       }
   public JTextField getDestBackupDirTextField() {
      return destBackupDirTextField;
      }
   public JTextField getDestBackupNameTextField() {
      return destBackupNameTextField;
      }
   public JLabel getDestBackupPathLabel() {
      return destBackupPathLabel;
      }
   public JCheckBox getDestArchivePromptCheckBox() {
      return destArchivePromptCheckBox;
      }
   public JTextField getDestArchiveDirTextField() {
      return destArchiveDirTextField;
      }
   public JButton getDestArchiveChooserButton() {
      return destArchiveChooserButton;
      }
   public JLabel getDestArchiveTagLabel() {
      return destArchiveTagLabel;
      }
   public JLabel getDestArchivePathLabel() {
      return destArchivePathLabel;
      }
   public JTextArea getLogTextArea() {
      return logTextArea;
      }
   public JScrollPane getLogScrollPane() {
      return logScrollPane;
      }
   public JButton getExitButton() {
      return exitButton;
      }

   //
   // Action Listeners for Menu Items
   //
   public void setupMenuCallbacks() {
      languagesMenuItemButtonShow.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { languagesMenuItemAction(); }
         } );
      languagesMenuItemButtonHide.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { languagesMenuItemAction(); }
         } );
      filtersMenuItemButtonOn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { filtersMenuItemAction(); }
         } );
      filtersMenuItemButtonOff.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { filtersMenuItemAction(); }
         } );
      profilesMenuItemButtonOn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { profilesMenuItemAction(); }
         } );
      profilesMenuItemButtonOff.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { profilesMenuItemAction(); }
         } );
      exportMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { exportMenuItemAction(); }
         } );
      importMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { importMenuItemAction(); }
         } );
      optionsMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { optionsMenuItemAction(); }
         } );
      //Skin (Look and Feel)
      String currentSkinName = UserPreferences.readPref(DataModel.prefSkinName);
      currentSkinName = UIManager.getLookAndFeel().getClass().getName();
      JRadioButtonMenuItem skinRbmi;
      ButtonGroup skinGroup = new ButtonGroup();
      for (UIManager.LookAndFeelInfo laf : UIProperties.lafs) {
         skinRbmi = new JRadioButtonMenuItem(laf.getName(),
            laf.getClassName().equals(currentSkinName));
         skinRbmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               skinMenuItemAction(e.getActionCommand()); }
            } );
         skinGroup.add(skinRbmi);
         skinMenuItem.add(skinRbmi); //menu: File | Look & Feel | ZZZZ
         }
      exitMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { exitMenuItemAction(); }
         } );
      guideMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { guideMenuItemAction(); }
         } );
      updatesMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { updatesMenuItemAction(); }
         } );
      aboutMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { aboutMenuItemAction(); }
         } );
      }

   //
   // Action Listeners for UI Controls
   //
   public void setupCallbacks() {

      //Setup Callbacks for Profiles Controls
      profilesDropDown.addItemListener(new ItemListener() {
         public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) profilesSelectAction(); }
         } );
      profilesAddButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { profilesNewButtonAction(); }
         } );
      profilesDeleteButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { profilesDeleteButtonAction(); }
         } );

      //Setup Callbacks for Source (Zip List) Controls
      srcZipList.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) { srcZipListSelection(); }
         } );
      srcAddFileButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            srcAddButtonAction(JFileChooser.FILES_ONLY); }
         } );
      srcAddFolderButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            srcAddButtonAction(JFileChooser.DIRECTORIES_ONLY); }
         } );
      srcRemoveButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { srcRemoveButtonAction(); }
         } );
      srcFilterButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) { srcFilterButtonAction(); }
          } );

      //Setup Callbacks for Destination (Backup & Archive) Controls
      destBackupDirTextField.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) { destBackupDirTextFieldEdited(); }
         });
      destBackupNameTextField.addKeyListener(new KeyAdapter(){
         @Override
         public void keyReleased(KeyEvent e) { destBackupNameTextFieldEdited(); }
         });
      destBackupChooserButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { destBackupChooserButtonAction(); }
         } );
      destArchivePromptCheckBox.addItemListener(new ItemListener(){
         public void itemStateChanged(ItemEvent e) { destArchivePromptCheckBoxToggled(); }
         });
      destArchiveDirTextField.addKeyListener(new KeyAdapter(){
         @Override
         public void keyReleased(KeyEvent e){ destArchiveDirTextFieldEdited();}
         });
      destArchiveChooserButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { destArchiveChooserButtonAction(); }
         } );

      //Setup Callbacks for Button Controls
      saveButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { saveButtonAction(); }
         } );
      resetButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { resetButtonAction(); }
         } );
      doBackupButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { doBackupButtonAction(); }
         } );
      exitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { exitButtonAction(); }
         } );
       }

   //
   // Callback Methods (Event Actions)
   //

   //Menu Items Callbacks
   public void languagesMenuItemAction() {
      for (JPanel flagPanel : langFlagsPanels)
         flagPanel.setVisible(languagesMenuItemButtonShow.isSelected());
      DataModel.saveShowLanguagesSetting(this);
      }
   public void filtersMenuItemAction() {
      //boolean filtersEnabled = filtersMenuItemButtonOn.isSelected();
      //srcFilterButton.setVisible(filtersEnabled);
      DataModel.buildZipListModel(getSrcZipListModel(), this);
      DataModel.updateSrcButtons(this);
      DataModel.saveFiltersEnabledSetting(this);
      }
   public void profilesMenuItemAction() {
      boolean showProfiles = profilesMenuItemButtonOn.isSelected();
      if (!showProfiles)
         popupMsg(Str.macroExpand(ui.profilesOffMsg, getCurrentProfileName()),
            ui.menuItemProfiles);
      profilesInnerPanel.setVisible(showProfiles);
      DataModel.saveShowProfilesSetting(this);
      }
   public void skinMenuItemAction(String actionCmd) {
      int count = 0;
      while (count < UIProperties.lafs.length &&
            !actionCmd.equals(UIProperties.lafs[count].getName()))
         count++;
      try {
         UIManager.LookAndFeelInfo laf = UIProperties.lafs[count];
         UIManager.setLookAndFeel(laf.getClassName());
         SwingUtilities.updateComponentTreeUI(this);

         //FIX NULL ICON PROBLEM ???
         //destBackupChooserButton.setIcon(Icons.folderIcon);
         //static JButton      destBackupChooserButton =   new JButton(Icons.folderIcon);
         //Icons.folderIcon == null ? 'Choose...' : Icons.folderIcon);

         pack();
         UIUtilities.lockInMinSize(this);
         popupMsg(Str.macroExpand(UIProperties.skinSetMsg, laf.getName()),
            ui.menuItemSkin);
         DataModel.saveSkinSetting(laf.getClassName());
         }
      catch (Exception except) {
         popupMsg(UIProperties.skinErrMsg + SystemAttributes.newLine +
            except.getMessage(), ui.menuItemSkin);
         }
      }
   public void exitMenuItemAction() {
      DataModel.exit();
      }
   public void exportMenuItemAction() {
      new ExportDialog(this);
      }
   public void importMenuItemAction() {
      new ImportDialog(this);
      }
   public void optionsMenuItemAction() {
      String oldNumRowsSrc = UserPreferences.readPref(Options.prefNumRowsSrc);
      String oldNumRowsLog = UserPreferences.readPref(Options.prefNumRowsLog);
      new OptionsDialog(this, destBackupNameTextField.getText());
      DataModel.updateDestPaths(this);
      if (!oldNumRowsSrc.equals(UserPreferences.readPref(Options.prefNumRowsSrc)) ||
         !oldNumRowsLog.equals(UserPreferences.readPref(Options.prefNumRowsLog))) {
         UIUtilities.setInitialNumRows(this);
         //Below lines are hack to force resize of frame (freaky!)
         this.setSize(this.getWidth()+1, this.getHeight());  //N
         this.validate();   //N
         this.pack();   //N
         this.setSize(this.getWidth()+1, this.getHeight());  //N
         this.validate();  //N
         this.pack();  //N
         }
      }
   public void guideMenuItemAction() {
      new UserGuideDialog(this);
      }
   public void updatesMenuItemAction() {
      String latestVersion = CheckForUpdates.getLatestVersion();
      String nl = SystemAttributes.newLine;
      String sp = SystemAttributes.space;
      if (latestVersion == null)
         popupMsg(ui.updatesErrMsg1 + nl + nl + ui.updatesErrMsg2, ui.menuItemUpdates);
      else if (latestVersion.equals(SystemAttributes.appVersion))
         popupMsg(ui.updatesVersionYours + sp + SystemAttributes.appVersion +
            nl + nl + ui.updatesVersionIsCurrent, ui.menuItemUpdates);
      else {
         String[] options = { ui.updatesButtonDownload, ui.updatesButtonCancel } ;
         if (JOptionPane.showOptionDialog(this,
               ui.updatesVersionYours + sp + SystemAttributes.appVersion +
               nl + nl + ui.updatesVersionNew + sp + latestVersion,
               ui.menuItemUpdates, JOptionPane.YES_NO_OPTION,
               JOptionPane.PLAIN_MESSAGE, null, options,
               ui.updatesButtonDownload) == 0)
            Browser.open(SystemAttributes.downloadURL);
         }
      }
   public void aboutMenuItemAction() {
      new AboutDialog(this);
      }

   //Language Icon Callbacks
   public void selectLanguage(String localeCode) {
      UserPreferences.saveLocalePref(localeCode);
      AppProperties.reload();
      new Application();
      this.setEnabled(false);
      this.dispose();
      }

   //Profiles Callbacks
   public void profilesSelectAction() {
       setCurrentProfile((String)profilesDropDown.getSelectedItem());
       DataModel.loadProfile(this);
       UserPreferences.savePref(DataModel.prefCurrentProfile, getCurrentProfileName());
       }
   public void profilesNewButtonAction() {
      String profileName = JOptionPane.showInputDialog(this, ui.profilesAddPrompt,
         ui.profilesAddTitle, JOptionPane.PLAIN_MESSAGE);
      if (profileName != null) {
         profileName = profileName.trim();
         if (profileName.isEmpty()) {
            popupMsg(ui.profilesAddMsgBlank, ui.profilesTitle);
            profilesNewButtonAction();  //prompt again
            }
         else if (profileName.equalsIgnoreCase(getCurrentProfileName())) {
            //handles edge case for very first new profile added
            popupMsg(ui.profilesAddMsgExists, ui.profilesTitle);
            profilesNewButtonAction();  //prompt again
            }
         else if (UserPreferences.profileInDB(profileName)) {
            popupMsg(ui.profilesAddMsgExists, ui.profilesTitle);
            profilesNewButtonAction();  //prompt again
            }
         else {
            if (!UserPreferences.profileInDB())
               DataModel.saveSettings(this); //handles edge case for very first new profile added
            setCurrentProfile(profileName);
            destBackupNameTextField.setText(profileName);
            DataModel.saveSettings(this);  //copies last profile into new profile
            profilesDropDown.addItem(profileName);
            profilesDropDown.setSelectedItem(profileName);  //trigger callback
            profilesDeleteButton.setEnabled(true);
            }
          }
       }
   boolean promptDeleteProfile(String name) {
      return JOptionPane.showConfirmDialog(this,
         Str.macroExpand(ui.profilesDeletePrompt, name),
         ui.profilesDeleteTitle, JOptionPane.YES_NO_OPTION,
         JOptionPane.PLAIN_MESSAGE) == 0;
      }
   public void profilesDeleteButtonAction() {
      if (promptDeleteProfile(getCurrentProfileName())) {
         UserPreferences.deleteProfile();
         int loc = profilesDropDown.getSelectedIndex();
         profilesDropDown.removeItemAt(loc);
         profilesDropDown.setSelectedIndex(
            Math.min(loc, profilesDropDown.getItemCount() - 1));  //triggers callback
         profilesDeleteButton.setEnabled(profilesDropDown.getItemCount() > 1);
         }
      }

   //Source Callbacks
   public void srcZipListSelection() {
      DataModel.updateSrcButtons(this);
      }
   /*
   public void srcZipListScrollPane(MouseEvent e) {
      DataModel.updateSrcButtons();
      System.out.println('Mouse Event: ' + e.toString());
      }
      */
   public void srcAddButtonAction(int fileSelectionMode) {
      JFileChooser srcAddFileChooser = new JFileChooser();
      srcAddFileChooser.setFileSelectionMode(fileSelectionMode);
      //jFileChooserData.setCurrentDirectory(new File(nullStr));
      srcAddFileChooser.setFileHidingEnabled(!SystemAttributes.evilWinSys);
      //Above line enables Windows users to see the "Application Data" folder.
      //A better option would probably be to add a user set option to control this.
      final int returnStatus =
         //srcAddFileChooser.showDialog(this, ui.srcAddCmd);
         srcAddFileChooser.showOpenDialog(this);
      if (returnStatus == JFileChooser.APPROVE_OPTION)
         DataModel.addZipItem(
            srcAddFileChooser.getSelectedFile().getAbsolutePath(), this);
      }
   public void srcRemoveButtonAction() {
      DataModel.removeCurrentZipItem(this);
      }
   public void srcFilterButtonAction() {
      new FilterDialog(this);
      }


   //Destination Callbacks
   public void destChooserButtonAction(JTextField destDirTextField, String msg) {
      JFileChooser destFileChooser = new JFileChooser();
      destFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      destFileChooser.setCurrentDirectory(new File(destDirTextField.getText()));
      if (destFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
         destDirTextField.setText(destFileChooser.getSelectedFile().getAbsolutePath());
      }
   public void destBackupChooserButtonAction() {
      destChooserButtonAction(destBackupDirTextField, ui.destBackupCmd);
      }
   public void destArchiveChooserButtonAction() {
      destChooserButtonAction(destArchiveDirTextField, ui.destArchiveCmd);
      }
   public void destBackupDirTextFieldEdited() {
      DataModel.updateDestPaths(this);
      }
   public void destBackupNameTextFieldEdited() {
      int badKeyLoc = destBackupNameTextField.getText().indexOf(SystemAttributes.fileSeparator);
      while (badKeyLoc != -1) {
         destBackupNameTextField.setText(
            destBackupNameTextField.getText().substring(0, badKeyLoc) +
            destBackupNameTextField.getText().substring(badKeyLoc + 1));
         badKeyLoc = destBackupNameTextField.getText().indexOf(SystemAttributes.fileSeparator);
         }
      DataModel.updateDestPaths(this);
      }
   public void destArchivePromptCheckBoxToggled() {
      DataModel.updateArchiveDir(this);
      }

   //Button Callbacks
   public void destArchiveDirTextFieldEdited() {
      DataModel.updateDestPaths(this);
      }
   public void saveButtonAction() {
      popupMsg(DataModel.saveSettings(this), ui.buttonSave);
      }
   public void resetButtonAction() {
      popupMsg(DataModel.restoreDefaultSettings(this), ui.buttonReset);
      }
   public void doBackupButtonAction() {
      DataModel.doBackup();
      }
   public void exitButtonAction() {
      DataModel.exit();
      }

   }
