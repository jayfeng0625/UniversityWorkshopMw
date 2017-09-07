package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.model.Market;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
      String marketId;
      int marketIdNumber = 0;

      //1
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Gold")
            .currentPrice(500.9)
            .build());
      //2
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Silver")
            .currentPrice(375.2)
            .build());
      //3
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Platinum")
            .currentPrice(312.0)
            .build());
      //4
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Copper")
            .currentPrice(250.0)
            .build());
      //5
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Natural Gas")
            .currentPrice(300.7)
            .build());
      //6
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Coffee")
            .currentPrice(205.0)
            .build());
      //7
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Wheat")
            .currentPrice(175.4)
            .build());
      //8
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Cocoa")
            .currentPrice(225.1)
            .build());
      //9
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Cotton")
            .currentPrice(125.0)
            .build());
      //10
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, Market.builder()
            .marketId(marketId)
            .marketName("Sugar")
            .currentPrice(148.0)
            .build());
   }
}
