package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.integration.dto.MarketDto;

import java.util.List;
import java.util.stream.Collectors;

public class MarketDataTransformer {
   public static List<MarketDto> transform(List<Market> listOfMarkets) {
      return listOfMarkets.stream()
            .map(market -> MarketDto.builder()
                  .marketId(market.getMarketId())
                  .marketName(market.getMarketName().getMarketName())
                  .currentPrice(market.getCurrentPrice())
                  .build())
            .collect(Collectors.toList());
   }
}
