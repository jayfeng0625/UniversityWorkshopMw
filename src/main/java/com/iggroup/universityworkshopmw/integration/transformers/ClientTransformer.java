package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientTransformer {

   public static Client transform(ClientDto clientDto) {
      Double profitAndLoss = clientDto.getProfitAndLoss();
      Double safeProfitAndLoss = new Double( null == profitAndLoss ? "0" : profitAndLoss.toString() );
      return Client.builder()
         .clientId(clientDto.getClientId())
         .userName(clientDto.getUserName())
         .profitAndLoss(safeProfitAndLoss)
         .build();
   }

   public static ClientDto clientModelToClientDto(Client client) {
      return new ClientDto(
            client.getClientId(),
            client.getUserName(),
            client.getProfitAndLoss()
      );
   }

}
