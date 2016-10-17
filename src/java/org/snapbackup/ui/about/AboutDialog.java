////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AboutDialog.java                                                           //
//                                                                            //
// About Dialog:                                                              //
//    This object....                                                         //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.about;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.snapbackup.ui.Icons;
import org.snapbackup.ui.UIProperties;
import org.snapbackup.ui.UIUtilities;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.utilities.Browser;

public class AboutDialog extends JDialog {

   static final String space =           SystemAttributes.space;
   static final String nullStr =         SystemAttributes.nullStr;
   static final String tab =             SystemAttributes.tab;
   static final String comma =           SystemAttributes.comma;
   static final String newLine =         SystemAttributes.newLine;
   static final String startHtml =       SystemAttributes.startHtml;
   static final String endHtml =         SystemAttributes.startHtml;
   static final String[][] translators = SystemAttributes.appTranslators;
   static final String urlKey =          "url";  //used as key to save URL for each translator
   static final String hey =             "Hey, don't press the '[' key!";

   AboutUIProperties ui = new AboutUIProperties();
   final String copyrightStr =   startHtml + "<small>" +
      SystemAttributes.appCopyright + "</small>" + endHtml;
   final String licenseStr =     ui.aboutLicense + newLine + newLine +
      ui.aboutDownload + newLine + tab + SystemAttributes.downloadURL;
   // final String contactInfoStr = (ui.aboutContact + newLine +
   //    SystemAttributes.postalAddress + newLine + SystemAttributes.homeURL +
   //    newLine + SystemAttributes.feedbackEMail).replaceAll(newLine, newLine + tab);

   //Define About Controls
   JPanel    aboutPanel =        new JPanel();
   JLabel    logo =              new JLabel(Icons.logoIcon);
   JLabel    productName =       new JLabel(UIProperties.current.header);
   JLabel    version =           new JLabel(ui.aboutVersion);
   JPanel    translatorsPanel =  new JPanel();
   JLabel    translatedBy =      new JLabel(ui.aboutTranslatedBy + space);
   JLabel    author =            new JLabel(ui.aboutCreatedBy);
   JLabel    copyright =         new JLabel(copyrightStr);
   JTextArea license =           new JTextArea(licenseStr);
   JTextArea configInfo =        new JTextArea();
   // JTextArea contactInfo =       new JTextArea(contactInfoStr);
   JButton   webButton =         new JButton(ui.aboutButtonWeb);
   JButton   closeButton =       new JButton(ui.aboutButtonClose);
   JButton[] buttonList =        { webButton, closeButton };

   public AboutDialog(Frame owner) {
      super(owner);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setTitle(ui.aboutTitle + UIProperties.current.appTitle);
      configureContols();
      addContols();
      setModal(true);
      setResizable(false);
      pack();
      setLocationRelativeTo(owner);
      setVisible(true);
      }

   void setupTranslatorsPanel() {
      translatorsPanel.setLayout(new BoxLayout(translatorsPanel, BoxLayout.PAGE_AXIS));
      JPanel row = null;
      int count = 0;
      for (String[] translator : translators) {
         if (count % 3 == 0) {  //three names per line
            row = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            row.setBackground(Color.white);
            row.add(count == 0 ? translatedBy : new JLabel(tab));
            translatorsPanel.add(row);
            }
         JLabel translatorLabel = new JLabel(startHtml + translator[0] + endHtml);
         translatorLabel.putClientProperty(urlKey, translator[1]);
         if (translator[1] != null)
            translatorLabel.addMouseListener(new MouseListener() {
               public void mouseClicked(MouseEvent e) {
                  Browser.open((String)((JLabel)e.getSource()).getClientProperty(urlKey));
                  }
               public void mouseEntered(MouseEvent e) {
                  setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                  }
               public void mouseExited(MouseEvent e) {
                  setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                  }
               public void mousePressed(MouseEvent e)  { /* do nothing */ }
               public void mouseReleased(MouseEvent e) { /* do nothing */ }
               });
         row.add(translatorLabel);
         String join = comma + space;
         if (count+2 == translators.length) join = join + ui.aboutAnd + space;
         if (count+1 == translators.length) join = nullStr;
         row.add(new JLabel(join));
         count = count + 1;
         }
      }

   void configureContols() {
      getContentPane().setBackground(Color.white);
      logo.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
      aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.PAGE_AXIS));
      aboutPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 30));
      aboutPanel.setBackground(Color.white);
      setupTranslatorsPanel();
      license.setEditable(false);
      license.setFont(UIProperties.standardFont);
      license.setBackground(Color.white);  //needed for Motif
      configInfo.setEditable(false);
      configInfo.setBackground(Color.white);  //needed for Motif
      configInfo.setFont(UIProperties.standardFont);
      configInfo.setText(
         UIProperties.current.appTitle + space + SystemAttributes.appVersion + newLine +
         SystemAttributes.osInfo + newLine +
         SystemAttributes.javaVersion + newLine +
         SystemAttributes.javaHomeDir + newLine +
         SystemAttributes.javaVMInfo);
      configInfo.setBorder(BorderFactory.createTitledBorder(ui.aboutCfgTitle));
      // contactInfo.setEditable(false);
      // contactInfo.setFont(UIProperties.standardFont);
      // contactInfo.setBackground(Color.white);  //needed for Motif
      webButton.setBackground(Color.white);
      webButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { Browser.open(SystemAttributes.visitURL); }
         } );
      closeButton.setBackground(Color.white);
      closeButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             closeButtonAction(); }
          } );
      UIUtilities.addFastKeys(buttonList);
      UIUtilities.makeBold(closeButton);
      getRootPane().setDefaultButton(closeButton);
      getRootPane().registerKeyboardAction(  //WARNING: Obsolete method
         new ActionListener() {
            public void actionPerformed(ActionEvent e) { closeButtonAction(); } },
         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),  //press esc key
         JComponent.WHEN_IN_FOCUSED_WINDOW);
      getRootPane().registerKeyboardAction(  //WARNING: Obsolete method
         new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
               JOptionPane.showMessageDialog(null, hey, ui.aboutVersion,
                  JOptionPane.PLAIN_MESSAGE); } },  //null should really be AboutDialog
         KeyStroke.getKeyStroke(KeyEvent.VK_OPEN_BRACKET, 0),  //don't press '['
         JComponent.WHEN_IN_FOCUSED_WINDOW);
      }

   public void addContols() {
      aboutPanel.add(productName);
      aboutPanel.add(version);
      aboutPanel.add(translatorsPanel);
      aboutPanel.add(author);
      aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      aboutPanel.add(license);
      aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      aboutPanel.add(configInfo);
      aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      // aboutPanel.add(contactInfo);
      // aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      aboutPanel.add(copyright);
      aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      aboutPanel.add(webButton);
      aboutPanel.add(Box.createRigidArea(new Dimension(0,10)));
      aboutPanel.add(closeButton);
      for (Component component : aboutPanel.getComponents())
         ((JComponent)component).setAlignmentX(Component.LEFT_ALIGNMENT);
      //for (int count = 0; count < aboutPanel.getComponentCount(); count++)
      //   ((JComponent)aboutPanel.getComponent(count)).setAlignmentX(Component.LEFT_ALIGNMENT);
      getContentPane().add(logo, BorderLayout.LINE_START);
      getContentPane().add(aboutPanel, BorderLayout.CENTER);
      }

   //
   // Callback Methods (Event Actions)
   //
   public void closeButtonAction() {
      this.dispose();
      }

}
