////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AboutUIProperties.java                                                     //
//                                                                            //
// About UI Properties:                                                       //
//    This object....                                                         //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) Individual contributors     //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.filter;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class FilterUIProperties {

   final String space = SystemAttributes.space;
   public final String filterRuleTitle =                AppProperties.getProperty("FilterRuleTitle");
   public final String filterRuleTag =                  AppProperties.getProperty("FilterRuleTag");
   public final String filterRuleIncludeTitle =         AppProperties.getPropertyPadded("FilterRuleIncludeTitle");
   public final String filterRuleIncludePrompt =        AppProperties.getProperty("FilterRuleIncludePrompt") + space;
   public final String filterRuleIncludeHelp =          AppProperties.getProperty("FilterRuleIncludeHelp");
   public final String filterRuleExcludeTitle =         AppProperties.getPropertyPadded("FilterRuleExcludeTitle");
   public final String filterRuleExcludePrompt =        AppProperties.getProperty("FilterRuleExcludePrompt") + space;
   public final String filterRuleExcludeHelp =          AppProperties.getProperty("FilterRuleExcludeHelp");
   public final String filterRuleExcludeFoldersPrompt = AppProperties.getProperty("FilterRuleExcludeFoldersPrompt") + space;
   public final String filterRuleExcludeFoldersHelp =   AppProperties.getProperty("FilterRuleExcludeFoldersHelp");
   public final String filterRuleExcludeSizePrompt =    AppProperties.getProperty("FilterRuleExcludeSizePrompt") + space;
   public final String filterRuleExcludeSizeUnits =     AppProperties.getPropertyPadded("FilterMarkerUnits");
   public final String filterRuleNote =                 AppProperties.getProperty("FilterRuleNote");
   public final String filterRuleButtonDelete =         AppProperties.getProperty("FilterRuleButtonDelete");
   public final String filterRuleButtonCancel =         AppProperties.getProperty("ButtonCancel");
   public final String filterRuleButtonOk =             AppProperties.getProperty("ButtonOk");

}
