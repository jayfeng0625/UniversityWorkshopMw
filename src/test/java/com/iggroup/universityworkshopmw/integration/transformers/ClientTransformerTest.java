package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ClientTransformerTest {

   @Test
   public void shouldTransformClientDtoToClientModel() {
      ClientDto clientDto = new ClientDto("client_12345", "userName", Double.valueOf(400));
      Client client = ClientTransformer.clientDtoToClientModel(clientDto);
      assertEquals(clientDto.getClientId(), client.getClientId());
      assertEquals(clientDto.getUserName(), client.getUserName());
      assertThat(clientDto.getProfitAndLoss().doubleValue(), is(client.getProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientDtoWithNullValues() {
      ClientDto clientDto = new ClientDto(null, null, null);
      Client client = ClientTransformer.clientDtoToClientModel(clientDto);
      assertNull(client.getClientId());
      assertNull(client.getUserName());
      assertThat(Double.valueOf(0).doubleValue(), is(client.getProfitAndLoss()));
   }

   @Test
   public void shouldTransformClientModelToClientDto() {
      Client client = new Client("client_12345", "userName", 400);
      ClientDto clientDto = ClientTransformer.clientModelToClientDto(client);
      assertEquals(client.getClientId(), clientDto.getClientId());
      assertEquals(client.getUserName(), clientDto.getUserName());
      assertThat(Double.valueOf(client.getProfitAndLoss()), is(clientDto.getProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientWithPotentialNullValues() {
      Client client = new Client("client_12345", null, 400);
      ClientDto clientDto = ClientTransformer.clientModelToClientDto(client);
      assertEquals(client.getClientId(), clientDto.getClientId());
      assertNull(client.getUserName(), clientDto.getUserName());
      assertThat(client.getProfitAndLoss(), is(Double.valueOf(client.getProfitAndLoss())));
   }
}