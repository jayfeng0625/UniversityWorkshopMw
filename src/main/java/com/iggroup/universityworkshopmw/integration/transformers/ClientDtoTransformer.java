package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientDtoTransformer {

   public static ClientDto transform(Client client) {
      return ClientDto.builder()
         .clientId(client.getClientId())
         .userName(client.getUserName())
         .profitAndLoss(client.getProfitAndLoss())
         .build();
   }

}
