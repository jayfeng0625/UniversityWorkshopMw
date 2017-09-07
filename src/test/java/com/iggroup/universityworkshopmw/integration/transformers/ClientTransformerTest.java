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
      ClientDto clientDto = ClientDto.builder()
         .clientId("client_12345")
         .userName("userName")
         .profitAndLoss(Double.valueOf(400))
         .build();
      Client client = ClientTransformer.transform(clientDto);
      assertEquals(clientDto.getClientId(), client.getClientId());
      assertEquals(clientDto.getUserName(), client.getUserName());
      assertThat(clientDto.getProfitAndLoss().doubleValue(), is(client.getProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientDtoWithNullValues() {
      ClientDto clientDto = ClientDto.builder()
         .clientId(null)
         .userName(null)
         .profitAndLoss(null)
         .build();
      Client client = ClientTransformer.transform(clientDto);
      assertNull(client.getClientId());
      assertNull(client.getUserName());
      assertThat(Double.valueOf(0).doubleValue(), is(client.getProfitAndLoss()));
   }

   @Test
   public void shouldTransformClientModelToClientDto() {
      Client client = Client.builder()
         .clientId("client_12345")
         .userName("userName")
         .profitAndLoss(400)
         .build();
      ClientDto clientDto = ClientDtoTransformer.transform(client);
      assertEquals(client.getClientId(), clientDto.getClientId());
      assertEquals(client.getUserName(), clientDto.getUserName());
      assertThat(Double.valueOf(client.getProfitAndLoss()), is(clientDto.getProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientWithPotentialNullValues() {
      Client client = Client.builder()
         .clientId("client_1235")
         .userName(null)
         .profitAndLoss(400)
         .build();
      ClientDto clientDto = ClientDtoTransformer.transform(client);
      assertEquals(client.getClientId(), clientDto.getClientId());
      assertNull(client.getUserName(), clientDto.getUserName());
      assertThat(client.getProfitAndLoss(), is(Double.valueOf(client.getProfitAndLoss())));
   }
}