package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientTransformer {

   public static Client clientDtoToClientModel(ClientDto clientDto) {
      return new Client(
            clientDto.getClientId(),
            clientDto.getUserName(),
            clientDto.getProfitAndLoss()
      );
   }

   public static ClientDto clientModelToClientDto(Client client) {
      return new ClientDto(
            client.getClientId(),
            client.getUserName(),
            client.getProfitAndLoss()
      );
   }

}
