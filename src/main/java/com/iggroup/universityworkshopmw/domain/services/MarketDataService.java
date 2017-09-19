package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.model.Market;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

   private final OpenPositionsService openPositionsService;

   private Map<String, Market> marketIdToMarketModelMap = new ConcurrentHashMap<>();
   private final String ID_PREFIX = "market_";

   public MarketDataService(OpenPositionsService openPositionsService) {
      this.openPositionsService = openPositionsService;
      initialiseMarketModelMap();
   }

   public List<Market> getAllMarkets() {
      final ArrayList<Market> markets = new ArrayList<>(marketIdToMarketModelMap.values());
      log.info("Retrieving all markets={}", markets);
      return markets;
   }

   public Map<String, Double> getMarketPrices() {
      final Map<String, Double> idToPriceMap = marketIdToMarketModelMap.values().stream()
            .collect(Collectors.toMap(Market::getId, Market::getCurrentPrice));
      log.info("Retrieving all market prices={}", idToPriceMap);
      return idToPriceMap;
   }

   void updateMarket(Market market) {
      marketIdToMarketModelMap.put(market.getId(), market);
      openPositionsService.updateMarketValue(market.getId(), market.getCurrentPrice());
   }

   List<Map.Entry<String, Market>> getShuffledMapSubset() {
      List<Map.Entry<String, Market>> marketMapEntries = new ArrayList<>(marketIdToMarketModelMap.entrySet());
      Collections.shuffle(marketMapEntries);
      return marketMapEntries.subList(0, marketMapEntries.size() / 2);
   }

   private void initialiseMarketModelMap() {
      String id;
      int marketIdNumber = 0;

      //1
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Gold")
            .currentPrice(500.9)
            .build());
      //2
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Silver")
            .currentPrice(375.2)
            .build());
      //3
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Platinum")
            .currentPrice(312.0)
            .build());
      //4
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Copper")
            .currentPrice(250.0)
            .build());
      //5
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Natural Gas")
            .currentPrice(300.7)
            .build());
      //6
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Coffee")
            .currentPrice(205.0)
            .build());
      //7
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Wheat")
            .currentPrice(175.4)
            .build());
      //8
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Cocoa")
            .currentPrice(225.1)
            .build());
      //9
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Cotton")
            .currentPrice(125.0)
            .build());
      //10
      id = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(id, Market.builder()
            .id(id)
            .marketName("Sugar")
            .currentPrice(148.0)
            .build());
   }
}
