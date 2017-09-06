package com.iggroup.universityworkshopmw.integration;

import com.iggroup.universityworkshopmw.TestHelper;
import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.domain.services.ClientService;
import com.iggroup.universityworkshopmw.integration.controllers.ClientController;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientFlowIntegrationTest {

   private ClientService clientService = spy(new ClientService());
   private ClientController clientController = new ClientController(clientService);
   private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

   @Test
   public void clientFlow() throws Exception {
      //Given
      String clientId;
      ClientDto clientDto = new ClientDto(null, "userName", null);

      /** Create Client */
      //When
      MvcResult mvcResult = mockMvc.perform(post("/client/createClient")
            .contentType(TestHelper.APPLICATION_JSON_UTF8)
            .content(TestHelper.convertObjectToJsonBytes(clientDto))
      )
            //Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestHelper.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.clientId", containsString("client_")))
            .andExpect(jsonPath("$.profitAndLoss", is(10000.0))).andReturn();

      clientId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.clientId");

      ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
      verify(clientService, times(1)).storeNewClient(clientArgumentCaptor.capture());
      verifyNoMoreInteractions(clientService);
      Client client = clientArgumentCaptor.getValue();
      assertNull(client.getClientId());
      assertThat(client.getProfitAndLoss(), is(0.0));
      assertThat(client.getUserName(), is("userName"));


      /** Retrieve profitAndLoss */
      //When
      MvcResult mvcResultProfitAndLoss = mockMvc.perform(get("/client/profitAndLoss/" + clientId))
            //Then
            .andExpect(status().isOk()).andReturn();

      final String content = mvcResultProfitAndLoss.getResponse().getContentAsString();
      assertEquals("10000.0", content);

      ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
      verify(clientService, times(1)).getProfitAndLoss(clientIdCaptor.capture());
      verifyNoMoreInteractions(clientService);

      String capturedClientId = clientIdCaptor.getValue();
      assertThat(capturedClientId, is(clientId));


      /** Handle NoAvailableDataException on retrieval of profitAndLoss */
      //When
      MvcResult mvcResultException = mockMvc.perform(get("/client/profitAndLoss/client_12345"))
            //Then
            .andExpect(status().isNotFound()).andReturn();

      final String contentException = mvcResultException.getResponse().getContentAsString();
      assertEquals("No available client data for clientId=client_12345", contentException);
   }
}
