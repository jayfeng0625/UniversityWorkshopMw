package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientDtoTransformer {

   public static ClientDto transform(Client client) {
      return ClientDto.builder()
         .id(client.getId())
         .userName(client.getUserName())
         .profitAndLoss(client.getProfitAndLoss())
         .build();
   }

}
