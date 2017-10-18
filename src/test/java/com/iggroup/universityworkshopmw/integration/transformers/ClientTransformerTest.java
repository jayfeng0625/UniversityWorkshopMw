package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import com.iggroup.universityworkshopmw.integration.dto.CreateClientDto;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ClientTransformerTest {

   @Test
   public void shouldTransformCreateClientDtoToClientModel() {
      CreateClientDto clientDto = CreateClientDto.builder()
         .userName("userName")
         .build();
      Client client = ClientTransformer.transform(clientDto);
      assertEquals(null, client.getId());
      assertEquals(clientDto.getUserName(), client.getUserName());
      assertThat(0.0, is(client.getAvailableFunds()));
      assertThat(0.0, is(client.getRunningProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientDtoWithNullValues() {
      CreateClientDto clientDto = CreateClientDto.builder()
         .userName(null)
         .build();
      Client client = ClientTransformer.transform(clientDto);
      assertNull(client.getId());
      assertNull(client.getUserName());
      assertThat(0.0, is(client.getAvailableFunds()));
      assertThat(0.0, is(client.getRunningProfitAndLoss()));
   }

}