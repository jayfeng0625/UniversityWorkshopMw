package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import com.iggroup.universityworkshopmw.integration.dto.AddOpenPositionDto;
import org.junit.Test;

import static com.iggroup.universityworkshopmw.integration.transformers.OpenPositionTransformer.transform;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class OpenPositionTransformerTest {

   @Test
   public void shouldTransformDto() {
      AddOpenPositionDto openPositionDto = createOpenPositionDto();
      OpenPosition openPosition = transform(openPositionDto);

      assertNull(openPosition.getId());
      assertThat(openPosition.getMarketId(), is("market_id"));
      assertThat(openPosition.getProfitAndLoss(), is(0.0));
      assertThat(openPosition.getOpeningPrice(), is(0.0));
      assertThat(openPosition.getBuySize(), is(10));
   }

   private AddOpenPositionDto createOpenPositionDto() {
      return AddOpenPositionDto.builder()
            .marketId("market_id")
            .buySize(10)
            .build();
   }
}
