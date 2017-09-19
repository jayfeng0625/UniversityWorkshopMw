package com.iggroup.universityworkshopmw.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
@Builder
public class Market {

   private String id;
   private String marketName;
   private Double currentPrice;

}
