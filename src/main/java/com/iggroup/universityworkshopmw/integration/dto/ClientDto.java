package com.iggroup.universityworkshopmw.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

   private String clientId;
   private String userName;
   private Double profitAndLoss;

}
