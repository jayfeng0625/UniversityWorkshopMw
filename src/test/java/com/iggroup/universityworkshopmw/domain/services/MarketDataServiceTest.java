package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.model.Market;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketDataServiceTest {

   private MarketDataService marketDataService;

   @Before
   public void setup() {
      //Given
      marketDataService = new MarketDataService();
   }

   @Test
   public void getAllMarkets_returnsAllMarkets_marketMapIsInitialised() {
      //When
      List<Market> allMarkets = marketDataService.getAllMarkets();

      //Then
      assertThat(allMarkets.size()).isEqualTo(10);
      assertThat(allMarkets).extracting(Market::getMarketName).containsOnly("Gold", "Silver", "Platinum", "Copper", "Natural Gas", "Coffee", "Wheat", "Cocoa", "Cotton", "Sugar");
      assertThat(allMarkets).extracting(Market::getMarketId).containsOnly("market_1", "market_2", "market_3", "market_4", "market_5", "market_6", "market_7", "market_8", "market_9", "market_10");
   }

   @Test
   public void getMarketPrices_returnsAllPricesForMarkets() {
      //When
      Map<String, Double> marketPrices = marketDataService.getMarketPrices();

      //Then
      assertThat(marketPrices.values().size()).isEqualTo(10);
      assertThat(marketPrices.keySet()).containsOnly("market_1", "market_2", "market_3", "market_4", "market_5", "market_6", "market_7", "market_8", "market_9", "market_10");
      assertThat(marketPrices.values()).containsOnly(500.9, 375.2, 312.0, 250.0, 300.7, 205.0, 175.4, 225.1, 125.0, 148.0);
   }
}