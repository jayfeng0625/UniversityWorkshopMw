package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.enums.MarketName;
import com.iggroup.universityworkshopmw.domain.model.Market;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.iggroup.universityworkshopmw.domain.enums.MarketName.*;

@Slf4j
@Component
public class MarketDataService {

   private Map<String, Market> marketIdToMarketModelMap = new ConcurrentHashMap<>();
   private final String ID_PREFIX = "market_";

   public MarketDataService() {
      initialiseMarketModelMap();
   }

   public List<Market> getAllMarkets() {
      final ArrayList<Market> markets = new ArrayList<>(marketIdToMarketModelMap.values());
      log.info("Retrieving all markets={}", markets);
      return markets;
   }

   public Map<String, Double> getMarketPrices() {
      final Map<String, Double> idToPriceMap = marketIdToMarketModelMap.values().stream()
            .collect(Collectors.toMap(Market::getMarketId, Market::getCurrentPrice));
      log.info("Retrieving all market prices={}", idToPriceMap);
      return idToPriceMap;
   }

   void updateMarket(Market market) {
      marketIdToMarketModelMap.put(market.getMarketId(), market);
   }

   List<Map.Entry<String, Market>> getShuffledMapSubset() {
      List<Map.Entry<String, Market>> marketMapEntries = new ArrayList<>(marketIdToMarketModelMap.entrySet());
      Collections.shuffle(marketMapEntries);
      return marketMapEntries.subList(0, marketMapEntries.size() / 2);
   }

   private void initialiseMarketModelMap() {
      IntStream.range(0, MarketName.values().length)
         .forEach(idx -> {
            String marketId = ID_PREFIX + (idx + 1);
            MarketName marketName = MarketName.values()[idx];
            Double startingPrice = marketName.getStartingPrice();

            marketIdToMarketModelMap.put(marketId, Market.builder()
               .marketId(marketId)
               .marketName(marketName)
               .currentPrice(startingPrice)
               .build());
         });
   }
}
