package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import com.iggroup.universityworkshopmw.integration.dto.OpenPositionDto;
import org.junit.Test;

import static com.iggroup.universityworkshopmw.integration.transformers.OpenPositionTransformer.transform;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OpenPositionTransformerTest {

   @Test
   public void shouldTransformDto() {
      OpenPositionDto openPositionDto = createOpenPositionDto();
      OpenPosition openPosition = transform(openPositionDto);

      assertThat(openPosition.getId(), is("open_position_id"));
      assertThat(openPosition.getMarketId(), is("market_id"));
      assertThat(openPosition.getProfitAndLoss(), is(1234.00));
      assertThat(openPosition.getOpeningPrice(), is(100.00));
      assertThat(openPosition.getBuySize(), is(10));
   }

   private OpenPositionDto createOpenPositionDto() {
      return OpenPositionDto.builder()
         .id("open_position_id")
         .marketId("market_id")
         .profitAndLoss(1234.00)
         .openingPrice(100.00)
         .buySize(10)
         .build();
   }
}
