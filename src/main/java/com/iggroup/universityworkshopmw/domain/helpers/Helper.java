package com.iggroup.universityworkshopmw.domain.helpers;

public class Helper {

   public static String createUniqueId(String prefix) {
      return prefix + System.currentTimeMillis();
   }
}
