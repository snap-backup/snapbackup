////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UIUtilities.java                                                           //
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
// UI Utilities:                                                              //
//    This object....                                                         //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui;

import java.awt.*;
import java.awt.event.ComponentEvent;
import javax.swing.*;
import org.snapbackup.ui.options.Options;
import org.snapbackup.settings.UserPreferences;

public class UIUtilities {

   private static final Dimension screenSize =
      Toolkit.getDefaultToolkit().getScreenSize();

   public static void makeBold (Component widget) {
      if (!UserPreferences.readLocalePref().equals("ko"))   //HACK!!!  refector with variable localeSupportsBold
         widget.setFont(new Font(widget.getFont().getFamily(), Font.BOLD,
            widget.getFont().getSize()));
      }

   public static void makeSmallBold (Component widget) {
      widget.setFont(new Font(widget.getFont().getFamily(), Font.BOLD,
         widget.getFont().getSize()-1));
      }

   public static void bumpUpFontSize (Component widget, int bumps) {
      if (!UserPreferences.readLocalePref().equals("ko")) {   //HACK!!!  refector with variable localeSupportsBold
         Font f = widget.getFont();
         widget.setFont(new Font(f.getFamily(), f.getStyle(), f.getSize() + bumps));
         }
      }

   public static void bumpUpFontSize (Component widget) {
      bumpUpFontSize (widget, 1);
      }

   public static void makeEmphasized(Component widget) {
      //Set to bold amd add color
      Font font = widget.getFont();
      if (!UserPreferences.readLocalePref().equals("ko"))   //HACK!!!  refector with variable localeSupportsBold
         widget.setFont(new Font(font.getFamily(), Font.BOLD, font.getSize()));
      widget.setForeground(UIProperties.emphasisColor);
      }

   public static void lockInMinSize(final JFrame frame) {
      //Ensures user cannot resize frame to be smaller than frame is right now.
      final int origX = frame.getSize().width;
      final int origY = frame.getSize().height;
      //frame.setSize(new Dimension(origX, origY));
      frame.addComponentListener(new java.awt.event.ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent event) {
            frame.setSize(Math.max(frame.getWidth(), origX),
               Math.max(frame.getHeight(), origY));
            frame.repaint();
            }
         });
      }

   public static void setInitialNumRows(SnapBackupFrame frame) {
//System.out.println("\nH1: " + frame.getHeight());
      frame.getSrcZipList().setVisibleRowCount(Integer.parseInt(
         UserPreferences.readPref(Options.prefNumRowsSrc)));
      frame.getLogTextArea().setRows(Integer.parseInt(
            UserPreferences.readPref(Options.prefNumRowsLog)));
//System.out.println("H2: " + frame.getHeight());
//System.out.println("H3: " + frame.getHeight());
      frame.pack();
      //frame.repaint();
      }

   private static int what(int forgndLen, int bkgndLen, int offset) {
      int x = offset + (bkgndLen - forgndLen)/2;
      return (x > 0 ? x : 0);
      }

   private static int what(int forgndLen, int bkgndLen) {
      return what(forgndLen, bkgndLen, 0);
      }

   public static void centerFrame(JFrame frame){
      Dimension frameSize = frame.getSize();
      frame.setLocation(
         what(frameSize.width, screenSize.width),
         what(frameSize.height, screenSize.height));
      frame.setVisible(true);
      }

   /*
   private static void centerDialog(JDialog dialog, Dimension bkgndSize,
         int offsetX, int offsetY) {
      Dimension dialogSize = dialog.getPreferredSize();
      dialog.setLocation(
         what(dialogSize.width, bkgndSize.width, offsetX),
         what(dialogSize.height, bkgndSize.height, offsetY));
      dialog.setVisible(true);
      }

   public static void centerDialog(JDialog dialog) {
      centerDialog(dialog, screenSize, 0, 0);
      }

   public static void centerDialog(JDialog dialog, JFrame frame) {
      centerDialog(dialog, frame.getSize(), frame.getLocation().x,
            frame.getLocation().y);
      }
   */

   public static void addFastKeys(JMenuBar menuBar) {
      for (Component menuComponent : menuBar.getComponents()) {  //iterate over menus
         JMenu menu = (JMenu)menuComponent;
         menu.setMnemonic(menu.getText().charAt(menu.getText().indexOf('&') + 1));
         menu.setText(menu.getText().replace("&", ""));
         for (Component menuItemComponent : menu.getMenuComponents())
            if (menuItemComponent instanceof JMenuItem) {   //skip separators
               JMenuItem menuItem = (JMenuItem)menuItemComponent;
               menuItem.setMnemonic(menuItem.getText().charAt(
                     menuItem.getText().indexOf('&') + 1));
               menuItem.setText(menuItem.getText().replace("&", ""));
               }
         /*
         for (int count = 0; count < menu.getMenuComponentCount(); count++) {  //iterate over menu items
            Component menuItemComponent = menu.getMenuComponent(count);
            if (menuItemComponent instanceof JMenuItem) {   //skip separators
               JMenuItem menuItem = (JMenuItem)menuItemComponent;
               menuItem.setMnemonic(menuItem.getText().charAt(
                     menuItem.getText().indexOf('&') + 1));
               menuItem.setText(menuItem.getText().replace("&", ""));
               }
            }
         */
         }
      }

   public static void addFastKeys(JButton[] buttonList) {
      for (JButton button : buttonList) {
         button.setMnemonic(button.getText().charAt(button.getText().indexOf('&') + 1));
         button.setText(button.getText().replace("&", ""));
         }
      }

   }
