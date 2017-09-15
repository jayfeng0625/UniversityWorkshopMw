package com.iggroup.universityworkshopmw.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MarketName {

   GOLD("Gold"),
   SILVER("Silver"),
   PLATINUM("Platinum"),
   COPPER("Copper"),
   NATURAL_GAS("Natural Gas"),
   COFFEE("Coffee"),
   WHEAT("Wheat"),
   COCOA("Cocoa"),
   COTTON("Cotton"),
   SUGAR("Sugar");

   private final String marketName;

}
