package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientTransformer {

   public static Client clientDtoToClientModel(ClientDto clientDto) {
      Double profitAndLoss = clientDto.getProfitAndLoss();
      Double safeProfitAndLoss = new Double( null == profitAndLoss ? "0" : profitAndLoss.toString() );
      return new Client(
            clientDto.getClientId(),
            clientDto.getUserName(),
            safeProfitAndLoss
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
