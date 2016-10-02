////////////////////////////////////////////////////////////////////////////////
// TopMap.java                                                                //
//                                                                            //
// GPLv3 -- snapback.org/license -- Copyright (c) individual contributors     //
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
