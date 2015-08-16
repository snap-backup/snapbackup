////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ImportDataModel.java                                                       //
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
// Import Data Model:                                                         //
//    This object is the business logic of MVC.                               //
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
      //See ExportDataModel.dataToXml
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
         //Validate XML against XSD
         InputStream schemaInput =
            getClass().getResourceAsStream("SnapBackupSettings.xsd");
         Schema schema = SchemaFactory.newInstance(
            XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(schemaInput));
         schema.newValidator().validate(new StreamSource(new File(fileName)));

         //Process XML
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
