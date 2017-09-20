package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;

public class ClientTransformer {

   public static Client transform(ClientDto clientDto) {
      Double funds = clientDto.getFunds();
      Double safeFunds = new Double( null == funds ? "0" : funds.toString() );
      return Client.builder()
         .id(clientDto.getId())
         .userName(clientDto.getUserName())
         .funds(safeFunds)
         .build();
   }

}
