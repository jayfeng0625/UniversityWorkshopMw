package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import com.iggroup.universityworkshopmw.integration.dto.OpenPositionDto;

public class OpenPositionTransformer {

   public static OpenPosition transform(OpenPositionDto openPositionDto) {
      return OpenPosition.builder()
         .id(openPositionDto.getId())
         .marketId(openPositionDto.getMarketId())
         .buySize(openPositionDto.getBuySize())
         .openingPrice(openPositionDto.getOpeningPrice())
         .profitAndLoss(openPositionDto.getProfitAndLoss())
         .build();
   }
}
