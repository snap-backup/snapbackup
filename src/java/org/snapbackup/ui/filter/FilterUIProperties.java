////////////////////////////////////////////////////////////////////////////////
// Snap Backup                                                                //
// AboutUIProperties.java                                                     //
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
// About UI Properties:                                                       //
//    This object....                                                         //
////////////////////////////////////////////////////////////////////////////////

package org.snapbackup.ui.filter;

import org.snapbackup.settings.AppProperties;
import org.snapbackup.settings.SystemAttributes;

public class FilterUIProperties {

   final String space = SystemAttributes.space;
   public final String filterRuleTitle =              AppProperties.getProperty("FilterRuleTitle");
   public final String filterRuleTag =                AppProperties.getProperty("FilterRuleTag");
   public final String filterRuleIncludeTitle =       AppProperties.getPropertyPadded("FilterRuleIncludeTitle");
   public final String filterRuleIncludePrompt =      AppProperties.getProperty("FilterRuleIncludePrompt") + space;
   public final String filterRuleIncludeHelp =        AppProperties.getProperty("FilterRuleIncludeHelp");
   public final String filterRuleExcludeTitle =       AppProperties.getPropertyPadded("FilterRuleExcludeTitle");
   public final String filterRuleExcludePrompt =      AppProperties.getProperty("FilterRuleExcludePrompt") + space;
   public final String filterRuleExcludeHelp =        AppProperties.getProperty("FilterRuleExcludeHelp");
   public final String filterRuleExcludeFoldersPrompt = AppProperties.getProperty("FilterRuleExcludeFoldersPrompt") + space;
   public final String filterRuleExcludeFoldersHelp =   AppProperties.getProperty("FilterRuleExcludeFoldersHelp");
   public final String filterRuleExcludeSizePrompt =  AppProperties.getProperty("FilterRuleExcludeSizePrompt") + space;
   public final String filterRuleExcludeSizeUnits =   AppProperties.getPropertyPadded("FilterMarkerUnits");
   public final String filterRuleNote =               AppProperties.getProperty("FilterRuleNote");
   public final String filterRuleButtonDelete =       AppProperties.getProperty("FilterRuleButtonDelete");
   public final String filterRuleButtonCancel =       AppProperties.getProperty("ButtonCancel");
   public final String filterRuleButtonOk =           AppProperties.getProperty("ButtonOk");

}
