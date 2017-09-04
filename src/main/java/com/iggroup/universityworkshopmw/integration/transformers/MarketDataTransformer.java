package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.integration.dto.MarketDto;

import java.util.List;
import java.util.stream.Collectors;

public class MarketDataTransformer {
   public static List<MarketDto> transformMarketListToMarketDtoList(List<Market> listOfMarkets) {
      return listOfMarkets.stream()
            .map(market -> new MarketDto(market.getMarketId(), market.getMarketName(), market.getCurrentPrice()))
            .collect(Collectors.toList());
   }
}
