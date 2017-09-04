package com.iggroup.universityworkshopmw.integration.dto;

public class MarketDto {

   private String marketId;
   private String marketName;
   private Double currentPrice;

   public MarketDto() {
   }

   public MarketDto(String marketId, String marketName, Double currentPrice) {
      this.marketId = marketId;
      this.marketName = marketName;
      this.currentPrice = currentPrice;
   }

   public String getMarketId() {
      return marketId;
   }

   public void setMarketId(String marketId) {
      this.marketId = marketId;
   }

   public String getMarketName() {
      return marketName;
   }

   public void setMarketName(String marketName) {
      this.marketName = marketName;
   }

   public Double getCurrentPrice() {
      return currentPrice;
   }

   public void setCurrentPrice(Double currentPrice) {
      this.currentPrice = currentPrice;
   }
}
