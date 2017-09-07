package com.iggroup.universityworkshopmw.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class Market {

   private String marketId;
   private String marketName;
   private Double currentPrice;

}
