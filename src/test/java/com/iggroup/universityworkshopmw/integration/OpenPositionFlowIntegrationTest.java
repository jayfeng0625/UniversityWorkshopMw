package com.iggroup.universityworkshopmw.integration;

import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.domain.services.ClientService;
import com.iggroup.universityworkshopmw.domain.services.OpenPositionsService;
import com.iggroup.universityworkshopmw.integration.controllers.OpenPositionsController;
import com.iggroup.universityworkshopmw.integration.dto.OpenPositionDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static com.iggroup.universityworkshopmw.TestHelper.APPLICATION_JSON_UTF8;
import static com.iggroup.universityworkshopmw.TestHelper.convertObjectToJsonBytes;
import static com.iggroup.universityworkshopmw.integration.transformers.OpenPositionTransformer.transform;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OpenPositionFlowIntegrationTest {

   private MockMvc mockMvc;

   private OpenPositionsController openPositionsController;
   private ClientService clientService = spy(ClientService.class);
   private OpenPositionsService openPositionsService = spy(new OpenPositionsService(clientService));

   @Before
   public void setup() {
      openPositionsController = new OpenPositionsController(openPositionsService);
      mockMvc = standaloneSetup(openPositionsController).build();
   }

   @Test
   public void openPositionFlow() throws Exception {
      Client client = clientService.storeNewClient(createClient());
      OpenPositionDto openPositionDto = createOpenPositionDto();
      mockMvc.perform(post("/openPositions/" + client.getId())
         .contentType(APPLICATION_JSON_UTF8)
         .content(convertObjectToJsonBytes(openPositionDto))
      )
         .andExpect(status().isOk())
         .andExpect(content().contentType(APPLICATION_JSON_UTF8))
         .andReturn();

      verify(openPositionsService, times(1)).addOpenPositionForClient(client.getId(), transform(openPositionDto));
      verify(clientService, times(1)).updateProfitAndLoss(client.getId(), 10000.00);
   }

   private Client createClient() {
      return Client.builder()
         .id("client_1")
         .profitAndLoss(10000)
         .userName("username")
         .build();
   }

   private OpenPositionDto createOpenPositionDto() {
      return OpenPositionDto.builder()
         .id("id1")
         .marketId("market_1")
         .profitAndLoss(1234.00)
         .openingPrice(100.00)
         .buySize(10)
         .build();
   }
}
