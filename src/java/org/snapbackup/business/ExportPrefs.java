////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// DataModel.java                                                             //
//                                                                            //
// Data Model:                                                                //
//    This object is the business logic of MVC.                               //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.business;

import java.io.File;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.swing.filechooser.FileFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.snapbackup.settings.UserPreferences;
import org.snapbackup.settings.SystemAttributes;

public class ExportPrefs {

   public static final String xmlExtension =            ".xml";
   public static final String xmlTopNodeName =          "snapbackup";
   public static final String xmlSettingsNodeName =     "usersettings";
   public static final String xmlSettingNodeName =      "setting";
   public static final String xmlSettingAttributeName = "key";
   public static final String xmlInfoNodeName =         "systeminformation";
   public static final String userHomeFolderMarker =    "[%HOME FOLDER%]";
   public final XmlFileFilter xmlFilter = new XmlFileFilter();

   public static String dataToXml (String data) {
      // See ImportDataModel.xmlToData
      return data.replace(
         //"<", "&lt;").replace(
         //">", "&gt;").replace(
         //"&", "&amp;").replace(
         SystemAttributes.userHomeDir, userHomeFolderMarker);
      }

   public class XmlFileFilter extends FileFilter {
      public boolean accept(File f) {
         return f.isDirectory() || f.getName().toLowerCase(Locale.ROOT).endsWith(xmlExtension);
         }
      public String getDescription() {
         return "XML files";
         }
      }

   String writeXmlFile(Document doc, String filename) {
      // Output DOM to an XML file
      String errMsg = null;
      try {
         Source source = new DOMSource(doc);
         Result result = new StreamResult(new File(filename));
         Transformer xformer = TransformerFactory.newInstance().newTransformer();
         xformer.setOutputProperty(OutputKeys.INDENT, "yes");  //human readable
         xformer.transform(source, result);
         }
      catch (Exception e) {
         errMsg = e.getMessage();
         }
      return errMsg;
      }

   public String doExport(String fileName) {
      // Create DOM with Top Level Node
      String errMsg = null;
      Document xmlDoc = null;
      DocumentBuilder xmlBuilder = null;
      try {
         xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         }
      catch (Exception e) {
         errMsg = e.getMessage();
         }
      xmlDoc = xmlBuilder.newDocument();
      Element topNode = xmlDoc.createElement(xmlTopNodeName);
      xmlDoc.appendChild(topNode);

      // Add System Info to DOM
      Element infoNode = xmlDoc.createElement(xmlInfoNodeName);
      topNode.appendChild(infoNode);
      infoNode.appendChild(xmlDoc.createTextNode(
         SystemAttributes.appName + SystemAttributes.space +
         SystemAttributes.appVersion + SystemAttributes.dividerStr +
         SystemAttributes.osInfo + SystemAttributes.dividerStr +
         SystemAttributes.javaVersion));

      // Add Settings Data to DOM
      String[] data = UserPreferences.getAllKeys();
      Element settingNode = xmlDoc.createElement(xmlSettingsNodeName);
      topNode.appendChild(settingNode);
      Element setting;
      for (String key : data) {
         setting = xmlDoc.createElement(xmlSettingNodeName);
         setting.setAttribute(xmlSettingAttributeName, key);
         setting.appendChild(xmlDoc.createTextNode(dataToXml(UserPreferences.readPrefByKey(key))));
         settingNode.appendChild(setting);
         }
      if (errMsg == null)
         errMsg = writeXmlFile(xmlDoc, fileName);
      return errMsg;
      }

}
