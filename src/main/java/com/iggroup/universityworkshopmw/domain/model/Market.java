package com.iggroup.universityworkshopmw.domain.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Market {

   private String marketId;
   private String marketName;
   private Double currentPrice;

   public Market(String marketId, String marketName, Double currentPrice) {
      this.marketId = marketId;
      this.marketName = marketName;
      this.currentPrice = currentPrice;
   }

   public void setCurrentPrice(Double currentPrice) {
      this.currentPrice = currentPrice;
   }
}
