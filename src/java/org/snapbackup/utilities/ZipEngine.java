////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// ZipEngine.java                                                             //
//                                                                            //
// Zip Engine:                                                                //
//    This object...                                                          //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.utilities;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import com.centerkey.utils.TopMap;
import org.snapbackup.logger.Logger;
import org.snapbackup.ui.UIProperties;
import org.snapbackup.ui.backupprogress.BackupProgressDialog;
import org.snapbackup.ui.options.Options;
import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;
import org.snapbackup.settings.UserPreferences;

public class ZipEngine {

   final static List<String> extraneousFiles =   Arrays.asList(".DS_Store", "Thumbs.db", "desktop.ini");
   final static List<String> extraneousFolders = Arrays.asList(".git", "node_modules", "$RECYCLE.BIN");
   final static List<String> cloudExtensions =   Arrays.asList("icloud");

   final static int kb =             1024;
   final static int buffSize =       kb * 256;  //optimal size not known
   final String nullStr =            SystemAttributes.nullStr;
   final String space =              SystemAttributes.space;
   final String tab =                SystemAttributes.tab;
   final String newLine =            SystemAttributes.newLine;
   final String fileSeparator =      SystemAttributes.fileSeparator;
   final String dividerStr =         SystemAttributes.dividerStr;
   final String dataPrompt =         SystemAttributes.dataPrompt;
   final String zippingLogMsg =      tab + tab + UIProperties.current.logMsgZipping;
   final String skippingLogMsg =     tab + tab + UIProperties.current.logMsgSkipping;
   final String folderLogMsg =       tab + UIProperties.current.logMsgFolder;
   final static String regExResChars = "-+?()[]{}|$^<=.";
   final static String regExEscCode =  "\\";
   final String exclusionNote =      " -- " + AppProperties.getProperty("FilterRuleExcludeTitle");
   final String cloudFileNote =      " -- " + AppProperties.getProperty("LabelWarning") + ": " +
      AppProperties.getProperty("FilterRuleExcludeCloud");
   final String sizePre =            space + space + "[";
   final String sizePost =           space + AppProperties.getProperty("FilterMarkerUnits") + "]";
   final String outline =            ")" + space;
   Locale locale = new Locale.Builder().setLanguage(UserPreferences.readLocalePref()).build();
   NumberFormat nf = NumberFormat.getNumberInstance(locale);
   int numLargestFiles;
   TopMap<Long, String> largestFiles;
   boolean useRelativePaths = true;
   String rootPath;
   int rootPathLen;
   File file;
   FileOutputStream fileOut = null;
   ZipOutputStream zipOut = null;
   ZipEntry zipEntry;
   boolean abortBackup;
   int zipCount;
   int byteCount;
   byte[] data = new byte[buffSize];  //data for read/write operations
   Runtime rt = Runtime.getRuntime();
   boolean filterIncludeOn, filterExcludeOn, filterFolderOn, filterSizeOn, showSkipped;
   Pattern filterIncludeP, filterExcludeP, filterFolderP;
   int filterSize;

   void initFilterInfo() {
      filterIncludeOn = false;
      filterExcludeOn = false;
      filterFolderOn =  false;
      filterSizeOn =    false;
      showSkipped = UserPreferences.readPref(Options.prefShowSkipped).equals(Options.skipYes);
      }

   Pattern compileRegEx(String regEx) {
      String regExEsc = regEx;
      Pattern pattern = null;
      for (char regExReservedChar : regExResChars.toCharArray())
         regExEsc = regExEsc.replace(
            String.valueOf(regExReservedChar), regExEscCode + regExReservedChar);
      try {
         pattern = Pattern.compile(regExEsc.replaceAll(", ", "|").replaceAll("\\*", ".*"));
         }
      catch (PatternSyntaxException e) {
         JOptionPane.showMessageDialog(null, UIProperties.current.err10CannotParseFilter +
               dataPrompt + regExEsc + dividerStr + e.getMessage(), regExEsc,
               JOptionPane.ERROR_MESSAGE);
         initFilterInfo();
         }
      return pattern;
      }

   void updateFilterInfo(List<List<String>> filterInfo, int loc) {
      filterIncludeOn = !filterInfo.get(0).get(loc).equals(nullStr);
      filterIncludeP =  compileRegEx(filterInfo.get(0).get(loc));
      filterExcludeOn = !filterInfo.get(1).get(loc).equals(nullStr);
      filterExcludeP =  compileRegEx(filterInfo.get(1).get(loc));
      filterFolderOn =  !filterInfo.get(2).get(loc).equals(nullStr);
      filterFolderP =   compileRegEx(filterInfo.get(2).get(loc));
      filterSizeOn =    !filterInfo.get(3).get(loc).equals(nullStr);
      if (filterSizeOn)
         filterSize = Integer.parseInt(filterInfo.get(3).get(loc)) * kb;
      }

   void logSkippedMsg(String msg) {
      if (showSkipped)
         Logger.logMsg(skippingLogMsg + msg);
      }

   public void zipFile(String filePath) {
      if (zipCount % 500 == 0)  //attempt to address performance issues on large backups
          rt.gc();  //testing not yet done to see if this improves performance
      try {
         file = new File(filePath);
         String filename = file.getName();
         String displayPath = useRelativePaths ? filePath.substring(rootPathLen) : filePath;
         if (filterIncludeOn && !filterIncludeP.matcher(filename).matches())
            logSkippedMsg(displayPath);
         else if (filterExcludeOn && filterExcludeP.matcher(filename).matches())
            logSkippedMsg(displayPath + exclusionNote);
         else if (filterSizeOn && file.length() > filterSize)
            logSkippedMsg(displayPath + exclusionNote + sizePre +
               nf.format(file.length() / kb) + sizePost);
         else if (cloudExtensions.contains(filename.substring(filename.lastIndexOf(".") + 1)))
            logSkippedMsg(displayPath + cloudFileNote);
         else if (!extraneousFiles.contains(filename)) {
            if (backupProgress != null)
               BackupProgressDialog.current.updateProgress(zipCount);
            Logger.logMsg(Str.macroExpand(zippingLogMsg, zipCount++) + displayPath);
            BufferedInputStream input =  //create buffered input stream from file input stream
               new BufferedInputStream(new FileInputStream(file));
            zipEntry = new ZipEntry(displayPath);
            zipEntry.setTime(file.lastModified());
            zipOut.putNextEntry(zipEntry);  //put empty zip entry into archive

            // Create a byte array object named data and declare byte count variable.
            while ((byteCount = input.read(data, 0, buffSize)) > -1)  //until input entirely read
               zipOut.write(data, 0, byteCount);
            zipOut.closeEntry();
            if (numLargestFiles > 0)
               largestFiles.put(zipEntry.getCompressedSize(), displayPath);
            }
         }
      catch (IOException e) {
         abortBackupAsk(UIProperties.current.err03ZippingFile + dataPrompt + filePath +
            dividerStr + e.getMessage());
         }
      }

   public void zipDirectory(String dirName, File dir) {
      String displayPath = useRelativePaths ? dirName.substring(rootPathLen) : dirName;
      Boolean isJunctionPoint = false;
      if (SystemAttributes.evilWinSys)
         try {
            Logger.logMsg(folderLogMsg + displayPath);
            Path path = Paths.get(dir.getAbsolutePath());
            BasicFileAttributes attrs =
               Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            isJunctionPoint = attrs.isOther();
            }
         catch (IOException e) {
            Logger.logMsg(e.toString());
            }
      if (filterFolderOn && filterFolderP.matcher(dir.getName()).matches())
         logSkippedMsg(displayPath + fileSeparator + exclusionNote);
      else if (extraneousFolders.contains(dir.getName()) || isJunctionPoint)
         logSkippedMsg(displayPath);
      else {
         Logger.logMsg(folderLogMsg + displayPath);
         File[] itemList = dir.listFiles();  //list folder's sub-items
         for (File item : itemList)
            if (!abortBackup)
               zipItem(item.getPath());
         }
      }

   public void zipItem(String item) {
      File itemObj = new File(item);
      if (itemObj.exists()) {
         if (itemObj.isDirectory())
            zipDirectory(item, itemObj);
         else
            zipFile(itemObj.getPath());
         }
      else
         abortBackupAsk(UIProperties.current.err02ItemNotFound, item);
      }

   public void calcRootPath(List<String> zipItemList) {
      String root = nullStr;
      boolean firstItem = true;
      for (String zipItem : zipItemList) {
         if (firstItem) root = zipItem;
         firstItem = false;
         if (zipItem.length() < root.length())
            root = root.substring(0, zipItem.length());
         for (int loc = 0; loc < root.length(); loc++)
            if (zipItem.charAt(loc) != root.charAt(loc))
               root = root.substring(0, loc);
         }
      rootPath = root.substring(0, root.lastIndexOf(fileSeparator) + 1);
      rootPathLen = rootPath.length();
      }

   public void zipItems(List<String> zipItemList, String zipFileName,
         boolean filtersOn, List<List<String>> filterInfo) {
      abortBackup = false;
      initFilterInfo();
      numLargestFiles = Integer.parseInt(UserPreferences.readPref(Options.prefNumLargestFiles));
      largestFiles = new TopMap<Long, String>(numLargestFiles);
      nf.setMaximumFractionDigits(1);
      Logger.logMsg(UIProperties.current.logMsgCreating + zipFileName);
      if (useRelativePaths) {
         calcRootPath(zipItemList);
         Logger.logMsg(UIProperties.current.logMsgRootPath + space + rootPath);
         }
      zipCount = 1;
      boolean invalidFolder =
         !FileSys.ensureParentFolderExists(zipFileName, backupProgress,
            UIProperties.current.fileUtilTitleBackupFolder);
      boolean writeDenided =
         UserPreferences.readPref(Options.prefAskBackup).equals(Options.askYes) &&
         new File(zipFileName).exists() &&
         !FileSys.askOverwrite(zipFileName, backupProgress,
            UIProperties.current.fileUtilTitleBackupFile);
      if (invalidFolder)
         abortBackup(UIProperties.current.err01CreatingBackupFile, FileSys.getErrMsg());  //better mesg?: 'No folder for backup file.'
      else if (writeDenided)
         abortBackup(UIProperties.current.err01CreatingBackupFile, FileSys.getErrMsg());  //better mesg?: 'No folder for backup file.'
      else
         try {
            fileOut = new FileOutputStream(zipFileName);
            zipOut = new ZipOutputStream(fileOut);
            /*
            for (String zipItem : zipItemList)
               if (!abortBackup) {
                  if (filtersOn)
                     updateFilterInfo(filterInfo, iter.nextIndex());  //nextIndex?
                  zipItem(zipItem);
                  }
            */
            ListIterator<String> iter = zipItemList.listIterator();
            while (iter.hasNext() && !abortBackup) {
               if (filtersOn)
                  updateFilterInfo(filterInfo, iter.nextIndex());
               zipItem(iter.next());
               }
            Locale locale = new Locale.Builder().setLanguage(UserPreferences.readLocalePref()).build();
            NumberFormat zipNF = NumberFormat.getNumberInstance(locale);
            zipNF.setMaximumFractionDigits(0);  //whole numbers only
            Logger.logMsg(UIProperties.current.logMsgFilesZipped + space +
               zipNF.format(zipCount - 1));
            zipOut.flush();
            zipOut.close();
            fileOut.close();
            int count = 1;
            if (numLargestFiles > 0) {
               Logger.logMsg(UIProperties.current.logMsgLargestFiles);
               for (Map.Entry<Long, String> largeFile : largestFiles.entrySet())
                  Logger.logMsg(tab + count++ + outline + largeFile.getValue() + sizePre +
                     zipNF.format(largeFile.getKey() / kb) + sizePost);
               }
            long backupKB = new File(zipFileName).length() / kb;
            Logger.logMsg(UIProperties.current.logMsgBackupCreated + space +
               zipFileName + sizePre + zipNF.format(backupKB) + sizePost);
            if (backupKB > 3800000) //Until Zip64 in Java 7, zip files are limited to 4 GB
               Logger.logMsg(UIProperties.current.err40BackupTooLarge);
            }
         catch (IOException e) {
            abortBackup(UIProperties.current.err01CreatingBackupFile, e.getMessage());
            }
      }

   public boolean isAbortSet() {
      return abortBackup;
      }

   public void abortBackup() {
      abortBackup = true;
      }
   public void abortBackup(String errMsg) {
      abortBackup(errMsg, "");
      }
   public void abortBackup(String errMsg, String supplimentalInfo) {
      abortBackup = true;
      if (backupProgress != null)
         JOptionPane.showMessageDialog(backupProgress, errMsg + newLine + supplimentalInfo);
      Logger.logMsg(errMsg);
      if (supplimentalInfo.length() > 0)
         Logger.logMsg(tab + supplimentalInfo);
      }

   BackupProgressDialog backupProgress = null;
   public void setBackupProgressDialog (BackupProgressDialog x) {
      backupProgress = x;
      }
   public BackupProgressDialog getBackupProgressDialog() {
      return backupProgress;
      }
   public void abortBackupAsk(String errMsg) {
      Logger.logMsg(errMsg);
      String[] options = { UIProperties.current.errPromptContinue,
              UIProperties.current.errPromptAbort };
      if (backupProgress != null)
         abortBackup = JOptionPane.showOptionDialog(backupProgress, errMsg,
            UIProperties.current.errPromptTitle,
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
            options, options[0]) == 1;
      }
   public void abortBackupAsk(String errMsg, String supplimentalInfo) {
      abortBackupAsk(errMsg + newLine + supplimentalInfo);
      if (supplimentalInfo.length() > 0)
         Logger.logMsg(tab + supplimentalInfo);
      }

}
