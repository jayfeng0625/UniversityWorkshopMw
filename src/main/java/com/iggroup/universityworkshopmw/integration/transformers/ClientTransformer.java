package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientTransformer {

   public static Client transform(ClientDto clientDto) {
      Double profitAndLoss = clientDto.getProfitAndLoss();
      Double safeProfitAndLoss = new Double( null == profitAndLoss ? "0" : profitAndLoss.toString() );
      return Client.builder()
         .id(clientDto.getId())
         .userName(clientDto.getUserName())
         .profitAndLoss(safeProfitAndLoss)
         .build();
   }

}
