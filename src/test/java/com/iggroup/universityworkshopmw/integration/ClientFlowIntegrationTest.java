package com.iggroup.universityworkshopmw.integration;

import com.iggroup.universityworkshopmw.domain.exceptions.DuplicatedDataException;
import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.domain.services.ClientService;
import com.iggroup.universityworkshopmw.integration.controllers.ClientController;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.iggroup.universityworkshopmw.TestHelper.APPLICATION_JSON_UTF8;
import static com.iggroup.universityworkshopmw.TestHelper.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpyBean(ClientService.class)
@SpringBootTest
public class ClientFlowIntegrationTest {

   @Autowired
   private ClientService clientService;
   @Autowired
   private ClientController clientController;
   private MockMvc mockMvc;

   @Before
   public void setup() {
      mockMvc = standaloneSetup(clientController).build();
   }

   @Test
   public void clientFlow() throws Exception {
      ClientDto clientDto = ClientDto.builder()
         .id(null)
         .userName("userName")
         .funds(null)
         .build();

      String clientId = mockCreateClient(clientDto);
      assertClient();
      String content = mockGetFunds(clientId);
      assertFunds(clientId, content);
      final String contentException = mockFundsException();

      assertEquals("No available client data for clientId=client_12345", contentException);
   }

   private String mockFundsException() throws Exception {
      MvcResult mvcResultException = mockMvc
         .perform(get("/client/funds/client_12345"))
         .andExpect(status().isNotFound())
         .andReturn();

      return mvcResultException.getResponse().getContentAsString();
   }

   private void assertFunds(String clientId, String content) throws NoAvailableDataException {
      assertEquals("10000.0", content);

      ArgumentCaptor<String> clientIdCaptor = ArgumentCaptor.forClass(String.class);
      verify(clientService, times(1)).getFunds(clientIdCaptor.capture());

      String capturedClientId = clientIdCaptor.getValue();
      assertThat(capturedClientId, is(clientId));
   }

   private String mockGetFunds(String clientId) throws Exception {
      MvcResult mvcResultFunds = mockMvc
         .perform(get("/client/funds/" + clientId))
         .andExpect(status().isOk())
         .andReturn();

      return mvcResultFunds.getResponse().getContentAsString();
   }

   private void assertClient() throws DuplicatedDataException {
      ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
      verify(clientService, times(1)).storeNewClient(clientArgumentCaptor.capture());
      verifyNoMoreInteractions(clientService);
      Client client = clientArgumentCaptor.getValue();
      assertNull(client.getId());
      assertThat(client.getFunds(), is(0.0));
      assertThat(client.getUserName(), is("userName"));
   }

   private String mockCreateClient(ClientDto clientDto) throws Exception {
      String clientId;
      MvcResult mvcResult = mockMvc.perform(post("/client/createClient")
            .contentType(APPLICATION_JSON_UTF8)
            .content(convertObjectToJsonBytes(clientDto))
      )
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id", containsString("client_")))
            .andExpect(jsonPath("$.funds", is(10000.0))).andReturn();

      clientId = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.id");
      return clientId;
   }
}
