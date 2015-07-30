////////////////////////////////////////////////////////////////////////////////
// TopMap.java                                                                //
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
////////////////////////////////////////////////////////////////////////////////

package com.centerkey.utils;

import java.util.Collections;
import java.util.TreeMap;

/**
 * <b>TopMap Elment Tracker</b><br>
 * Keeps track of the top (heaviest, furthest, oldest, longest, loudest,
 * fastest, etc.) elements.
 */
public class TopMap<K, V> extends TreeMap<Long, V> {

   private final int maxElems;

   public TopMap(int maxNumberElements) {
      super(Collections.reverseOrder());
      maxElems = maxNumberElements;
      }

   @Override
   public V put(Long key, V value) {
      while (containsKey(key)) key = key + 1;  //hack to support duplicates
      if (size() < maxElems || key > lastKey())
         super.put(key, value);
      if (size() > maxElems)
         remove(lastKey());
      return value;
      }

   }
