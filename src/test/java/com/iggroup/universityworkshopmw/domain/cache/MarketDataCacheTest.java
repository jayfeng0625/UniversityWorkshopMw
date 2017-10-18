package com.iggroup.universityworkshopmw.domain.cache;

import com.iggroup.universityworkshopmw.domain.caches.MarketDataCache;
import com.iggroup.universityworkshopmw.domain.enums.MarketName;
import com.iggroup.universityworkshopmw.domain.exceptions.NoMarketPriceAvailableException;
import com.iggroup.universityworkshopmw.domain.model.Market;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class MarketDataCacheTest {

   private MarketDataCache cache;
   private Market market;

   private static final String ID = "ID";
   private static final MarketName MARKET_NAME = MarketName.GOLD;
   private static final double PRICE = 100;

   @Before
   public void setup() {
      cache = new MarketDataCache();
      market = Market.builder()
            .id(ID)
            .marketName(MARKET_NAME)
            .currentPrice(PRICE)
            .build();
   }

   @Test
   public void shouldStoreMarketDataWhenIdAndMarketProvided() {
      // given

      // when
      cache.put(ID, market);

      // then
      assertEquals(market, cache.get(ID));
   }

   @Test
   public void shouldReturnTrueWhenQueryingExistingMarketID() {
      // given
      String id = "ID";

      // when
      cache.put(ID, market);

      // then
      assertTrue(cache.containsKey(id));
   }

   @Test
   public void shouldReturnCorrectMarketPriceWhenValidMarketIdProvided() throws NoMarketPriceAvailableException {
      // given
      cache.put(ID, market);

      // when
      double quotePrice = cache.getCurrentPriceForMarket(ID);

      // then
      assertTrue(quotePrice == PRICE);
   }

   @Test(expected = NoMarketPriceAvailableException.class)
   public void shouldThrowExceptionWhenMarketIsNull() throws NoMarketPriceAvailableException {
      // given
      market = null;

      // when
      cache.getCurrentPriceForMarket(ID);
   }

   @Test(expected = NullPointerException.class)
   public void shouldThrowExceptionWhenTryToPutNullMarket() {
      // given
      cache.put(ID, null);
      // when
      // then
   }

}
