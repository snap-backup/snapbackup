////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AppProperties.java                                                         //
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
// Application Properties:                                                    //
//    This object is for application specific settings related to the         //
//    properites file.                                                        //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.settings;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// Reads values from the properties file and provides access to those
// values.  Additional properties with values may added at run-time.
public class AppProperties {

   static final HashMap<String, String> supplimentalProperty =
      new HashMap<String, String>();

   static ResourceBundle applicationResources = getLocalizedBundle();

   static ResourceBundle getLocalizedBundle() {
      return ResourceBundle.getBundle(SystemAttributes.appName,
         new Locale(UserPreferences.readLocalePref()));
      }

   public static String getProperty(String propertyName) {
      //Reads a property from the ".properties" file.
      //String propertyValue;
      try {
         return applicationResources.getString(propertyName);
         }
      catch (MissingResourceException e) {
         String propertyValue = supplimentalProperty.get(propertyName);
         if (propertyValue == null) {
            propertyValue = SystemAttributes.errMsgHeader + propertyName +
               SystemAttributes.errMsgMissingResource +
               SystemAttributes.appName;
            System.out.println(propertyValue);
            }
         return propertyValue;
         }
      }

   public static boolean getBooleanProperty(String propertyName) {
      return getProperty(propertyName).equals(getProperty("True"));
      }

   public static String getPropertyPadded(String propertyName) {
      return " " + getProperty(propertyName) + " ";
      }

   public static String getPropertyHtml(String propertyName) {
      return SystemAttributes.startHtml + getProperty(propertyName) +
         SystemAttributes.endHtml;
      }

   public static void addSupplimentalProperty(String propertyName, String propertyValue) {
      supplimentalProperty.put(propertyName, propertyValue);
      //Locale[] locales = Locale.getAvailableLocales();
      //System.out.println("Locale: " + applicationResources.getLocale().getDisplayName() + ", " + locales.length);
      //System.out.println("Locale: " + Locale.getDefault().getDisplayName());

      //ListResourceBundle x;
      }

   public static void reload() {
      applicationResources = getLocalizedBundle();
      }

   }
