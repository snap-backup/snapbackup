////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UserGuideDialog.java                                                       //
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
// User Guide Dialog:                                                         //
//    This object...                                                          //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.userguide;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import org.snapbackup.ui.UIUtilities;
import edu.jhu.apl.PrintUtilities;

public class UserGuideDialog extends JDialog {

   UserGuideUIProperties ui = new UserGuideUIProperties();

   //Define About Controls
   JPanel      basePanel =      new JPanel();
   JPanel      headerPanel =    new JPanel();
   JPanel      buttonPanel =    new JPanel();
   JLabel      titleLabel =     new JLabel(ui.userGuideHeader);
   JLabel      versionLabel =   new JLabel(ui.userGuideVersion);
   JScrollPane htmlScrollPane = new JScrollPane();
   JEditorPane htmlEditorPane;
   JButton     printButton =    new JButton(ui.userGuideButtonPrint);
   JButton     closeButton =    new JButton(ui.userGuideButtonClose);
   JButton[]   buttonList =     { printButton, closeButton };

   public UserGuideDialog(Frame owner) {
      super(owner);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setTitle(ui.userGuideTitle);
      configureContols();
      addContols();
      setResizable(true);
      pack();
      setLocationRelativeTo(owner);
      setVisible(true);
      }

   public void configureContols() {
      basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.PAGE_AXIS));
      basePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
      headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.PAGE_AXIS));
      UIUtilities.makeBold(titleLabel);
      UIUtilities.bumpUpFontSize(titleLabel, 3);
      titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      URL url = getClass().getResource(UserGuideUIProperties.userGuideURL);
      try {
         htmlEditorPane = new JEditorPane(url);
         }
      catch (IOException e) {
         htmlEditorPane = new JEditorPane(UserGuideUIProperties.userGuideErrMsgMIME,
            ui.userGuideErrMsg + UserGuideUIProperties.userGuideURL);
         }
      htmlEditorPane.setEditable(false);
      int width =  getOwner().getWidth() *  UserGuideUIProperties.userGuideSizeScaleX / 100;
      int height = getOwner().getHeight() * UserGuideUIProperties.userGuideSizeScaleY / 100;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension size = new Dimension(
         Math.min(width,  screenSize.width - 200),
         Math.min(height, screenSize.height - 200));
      //htmlEditorPane.setSize(size);
      //htmlEditorPane.setPreferredSize(size);
      htmlScrollPane.setViewportView(htmlEditorPane);
      htmlScrollPane.setPreferredSize(size);
      //htmlScrollPane.setSize(size);
      //htmlScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      printButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) { printButtonAction(); }
          } );
      closeButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) { closeButtonAction(); }
          } );
      UIUtilities.addFastKeys(buttonList);
      UIUtilities.makeBold(closeButton);
      getRootPane().setDefaultButton(closeButton);
      getRootPane().registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               closeButtonAction(); } },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
      }

   public void addContols() {
      headerPanel.add(titleLabel);
      headerPanel.add(versionLabel);
      //basePanel.add(headerPanel, BorderLayout.PAGE_START);
      basePanel.add(headerPanel);
      //basePanel.add(htmlScrollPane, BorderLayout.CENTER);
      basePanel.add(htmlScrollPane);
      buttonPanel.add(printButton);
      buttonPanel.add(closeButton);
      //basePanel.add(buttonPanel, BorderLayout.PAGE_END);
      basePanel.add(buttonPanel);
      getContentPane().add(basePanel);
      }

   public void printButtonAction() {
      new PrintUtilities(htmlEditorPane).print();
      }

   public void closeButtonAction() {
      this.dispose();
      }

}
