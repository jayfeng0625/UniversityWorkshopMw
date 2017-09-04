package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.model.Market;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

   private void initialiseMarketModelMap() {
      String marketId;
      int marketIdNumber = 0;

      //1
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Gold", 500.9));
      //2
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Silver", 375.2));
      //3
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Platinum", 312.0));
      //4
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Copper", 250.0));
      //5
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Natural Gas", 300.7));
      //6
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Coffee", 205.0));
      //7
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Wheat", 175.4));
      //8
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Cocoa", 225.1));
      //9
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Cotton", 125.0));
      //10
      marketId = ID_PREFIX + ++marketIdNumber;
      marketIdToMarketModelMap.put(marketId, new Market(marketId, "Sugar", 148.0));

   }
}
