////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ImportDataModel.java                                                       //
//                                                                            //
// Import Data Model:                                                         //
//    This object is the business logic of MVC.                               //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.business;

import java.io.File;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;

public class ImportPrefs {

   public static String xmlToData (String xml) {
      // See: ExportDataModel.dataToXml
      return xml.replace(
         "&lt;", "<").replace(
         "&gt;", ">").replace(
         "&amp;", "&").replace(
         ExportPrefs.userHomeFolderMarker, SystemAttributes.userHomeDir);
      }

   String getNodeString(Node node) {
      return node == null ? "" : node.getNodeValue();
      }

   void loadSettings(NodeList settings) {
      String currentLocale = UserPreferences.readLocalePref();
      UserPreferences.deleteAllPrefs();
      for (int count = 0; count < settings.getLength(); count++)
         UserPreferences.savePrefByKey(
            xmlToData(settings.item(count).getAttributes().item(0).getNodeValue()),
            xmlToData(getNodeString(settings.item(count).getFirstChild())));
      UserPreferences.saveLocalePref(currentLocale);
      }

   public String doImport(String fileName) {
      String errMsg = null;
      try {
         // Validate XML against XSD
         InputStream schemaInput =
            getClass().getResourceAsStream("SnapBackupSettings.xsd");
         Schema schema = SchemaFactory.newInstance(
            XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(schemaInput));
         schema.newValidator().validate(new StreamSource(new File(fileName)));

         // Process XML
         Document xmlDoc;
         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder xmlBuilder = docBuilderFactory.newDocumentBuilder();
         xmlDoc = xmlBuilder.parse(new File(fileName));
         loadSettings(xmlDoc.getElementsByTagName(ExportPrefs.xmlSettingNodeName));
         }
      catch (Exception e) {
         errMsg = e.getMessage();
         }
      return errMsg;
      }

}
