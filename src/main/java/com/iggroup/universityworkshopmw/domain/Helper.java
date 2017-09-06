package com.iggroup.universityworkshopmw.domain;

import java.util.UUID;

public class Helper {

   public static String createUniqueId(String prefix) {
      return prefix + UUID.randomUUID().toString();
   }
}
