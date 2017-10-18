package com.iggroup.universityworkshopmw.integration.transformers;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ClientDtoTransformerTest {
   @Test
   public void shouldTransformClientModelToClientDto() {
      Client client = Client.builder()
            .id("client_12345")
            .userName("userName")
            .availableFunds(Double.valueOf(400))
            .runningProfitAndLoss(Double.valueOf(400))
            .build();
      ClientDto clientDto = ClientDtoTransformer.transform(client);
      assertEquals(client.getId(), clientDto.getId());
      assertEquals(client.getUserName(), clientDto.getUserName());
      assertThat(Double.valueOf(client.getAvailableFunds()), is(clientDto.getAvailableFunds()));
      assertThat(Double.valueOf(client.getRunningProfitAndLoss()), is(clientDto.getRunningProfitAndLoss()));
   }

   @Test
   public void shouldHandleClientWithPotentialNullValues() {
      Client client = Client.builder()
            .id("client_1235")
            .userName(null)
            .availableFunds(Double.valueOf(400))
            .runningProfitAndLoss(Double.valueOf(400))
            .build();
      ClientDto clientDto = ClientDtoTransformer.transform(client);
      assertEquals(client.getId(), clientDto.getId());
      assertNull(client.getUserName(), clientDto.getUserName());
      assertThat(client.getRunningProfitAndLoss(), is(Double.valueOf(client.getRunningProfitAndLoss())));
      assertThat(client.getAvailableFunds(), is(Double.valueOf(client.getAvailableFunds())));
   }
}