package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.integration.dto.MarketDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MarketDataTransformerTest {

   @Test
   public void transformMarketListToMarketDtoList_returnsListOfMarketDtos() {
      List<Market> listOfMarkets = new ArrayList<>();
      listOfMarkets.add(new Market("market_1", "commodity1", 100.0));

      List<MarketDto> marketDtos = MarketDataTransformer.transformMarketListToMarketDtoList(listOfMarkets);

      assertThat(marketDtos.size()).isEqualTo(1);
      assertThat(marketDtos.get(0).getMarketId()).isEqualTo("market_1");
      assertThat(marketDtos.get(0).getMarketName()).isEqualTo("commodity1");
      assertThat(marketDtos.get(0).getCurrentPrice()).isEqualTo(100.0);
   }
}