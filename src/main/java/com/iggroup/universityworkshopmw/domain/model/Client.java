package com.iggroup.universityworkshopmw.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class Client {

   private String clientId;
   private String userName;
   @Setter
   private double profitAndLoss;

}
