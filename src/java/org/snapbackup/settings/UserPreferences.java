////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// UserPreferences.java                                                       //
//                                                                            //
// User Preferences:                                                          //
//    This object is for user specific settings related to user preferences.  //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.snapbackup.ui.SnapBackupFrame;

public abstract class UserPreferences {

   public static final boolean defaultPrefShowLanguages =  true;
   public static final boolean defaultPrefFiltersEnabled = false;
   public static final boolean defaultPrefShowProfiles =   false;
   public static final boolean defaultPrefArchiveChecked = false;

   static Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
   static final String prefix = SystemAttributes.prefPrefix;
   static final String locale = SystemAttributes.prefLocale;
   static final String prefProfileName = "profilename";
   static final String prefValueNotFound = "NOT FOUND";
   static String cmdLineProfileName = null;

   public static String readLocalePref() {
      return prefs.get(prefix + locale, Locale.getDefault().getLanguage());
      }

   public static void saveLocalePref(String localeCode) {
      prefs.put(prefix + locale, localeCode);
      }

   public static String readPref(String prefName) {
      // Returns user's preference.  If none, default app property is returned.
      return prefs.get(prefix + prefName.toLowerCase(Locale.ROOT), AppProperties.getProperty(prefName));
      }

   public static boolean readPref(String prefName, boolean prefDefault) {
      // Returns user's preference.  If none, the default value is returned.
      return prefs.getBoolean(prefix + prefName.toLowerCase(Locale.ROOT), prefDefault);
      }

   public static void savePref(String prefName, String prefValue) {
      // Stores user's preference.
      prefs.put(prefix + prefName.toLowerCase(Locale.ROOT), prefValue);
      }

   public static void savePref(String prefName, boolean prefValue) {
      // Stores user's preference.
      prefs.putBoolean(prefix + prefName.toLowerCase(Locale.ROOT), prefValue);
      }

   public static void deletePref(String prefName) {
      // Erases user's preference, if it exists.
      prefs.remove(prefix + prefName.toLowerCase(Locale.ROOT));
      }

   public static boolean exists(String prefName) {
      // Returns true if the preference has a value.
      return !prefValueNotFound.equals(
         prefs.get(prefix + prefName.toLowerCase(Locale.ROOT), prefValueNotFound));
      }

   // Profiles

   public static void setCmdLineProfileName(String profileName) {
      cmdLineProfileName = profileName.toLowerCase(Locale.ROOT);
      }

   static String profilePrefix(String profileName) {
      return SystemAttributes.prefProfilePrefix + profileName.toLowerCase(Locale.ROOT) +
         SystemAttributes.prefProfilePostfix;
      }

   static String profilePrefix() {
      String profileName = cmdLineProfileName;
      if (profileName == null)
         profileName = SnapBackupFrame.current.getCurrentProfileName();
      return profilePrefix(profileName);
      }

   public static String getCurrentProfileName() {
      return cmdLineProfileName == null ?
         SnapBackupFrame.current.getCurrentProfileName() : cmdLineProfileName;
      }

   public static boolean profileInDB() {
      return prefs.get(profilePrefix() + prefProfileName, null) != null;
      }

   public static boolean profileInDB(String profileName) {
      return prefs.get(profilePrefix(profileName) + prefProfileName, null) != null;
      }

   public static String readProfilePref(String prefName) {
      // Returns the string value of the user's preference.  If none, the default app property is returned.
      return prefs.get(profilePrefix() + prefName.toLowerCase(Locale.ROOT),
         AppProperties.getProperty(prefName));
      }

   public static boolean readProfilePref(String prefName, boolean defaultValue) {
      // Returns the boolean value of the user's preference.  If none, the default value is returned.
      return prefs.getBoolean(profilePrefix() + prefName.toLowerCase(Locale.ROOT), defaultValue);
      }

   public static void saveProfilePref(String prefName, String prefValue) {
       // Stores the string value of a user's preference.
       prefs.put(profilePrefix() + prefName.toLowerCase(Locale.ROOT), prefValue);
       }

   public static void saveProfilePref(String prefName, boolean prefValue) {
       // Stores the boolean value of a user's preference.
       prefs.putBoolean(profilePrefix() + prefName.toLowerCase(Locale.ROOT), prefValue);
       }

   public static void deleteProfilePref(String prefName) {
      // Erases user's preference, if it exists.
      prefs.remove(profilePrefix() + prefName.toLowerCase(Locale.ROOT));
      }

   public static String[] getAllKeys() {
      String[] allKeys;
      try {
         allKeys = prefs.keys();
         java.util.Arrays.sort(allKeys);
         }
      catch (BackingStoreException e) {
         allKeys = new String[0];
         System.out.println(e.getMessage());
         }
      return allKeys;
      }

   public static List<String> getProfileNames() {
      List<String> names = new ArrayList<String>();
      for (String key : getAllKeys())
         if (key.endsWith(SystemAttributes.prefChar + prefProfileName))
            names.add(prefs.get(key, prefValueNotFound));
      return names;
      }

   public static void deleteProfile() {
      for (String key : getAllKeys())
         if (key.startsWith(profilePrefix()))
            prefs.remove(key);
      }

   // Export/Import
   public static String readPrefByKey(String key) {
      return prefs.get(key, prefValueNotFound);
      }

   public static void deleteAllPrefs() {
      for (String key : getAllKeys())
         prefs.remove(key);
      }

   public static void savePrefByKey(String key, String prefValue) {
      prefs.put(key, prefValue);
      }

}
