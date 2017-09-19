package com.iggroup.universityworkshopmw.domain.model;

import com.iggroup.universityworkshopmw.domain.enums.MarketName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Builder
public class Market {

   private String marketId;
   private MarketName marketName;
   private double currentPrice;

}
