package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.Client;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ClientServiceTest {

   private ClientService clientService;

   @Before
   public void setUp() {
      clientService = new ClientService();
   }

   @Test
   public void storeNewClient_assignsUniqueIdPerAddition() {
      Client client1 = createClient("", "userName1", 0);
      Client client2 = createClient("", "userName2", 0);

      Client returnClient1 = clientService.storeNewClient(client1);
      Client returnClient2 = clientService.storeNewClient(client2);

      assertNotNull(returnClient1.getClientId());
      assertNotNull(returnClient2.getClientId());
      assertNotEquals(returnClient1.getClientId(), returnClient2.getClientId());
   }

   @Test
   public void getFunds_getsFundsForClientId() throws NoAvailableDataException {
      Client returnClient1 = clientService.storeNewClient(createClient("", "userName1", 0));
      String clientId = returnClient1.getClientId();
      double expectedFunds = returnClient1.getFunds();

      double actualFunds = clientService.getFunds(clientId);

      assertThat(expectedFunds, is(actualFunds));
   }

   @Test(expected = NoAvailableDataException.class)
   public void getFunds_handlesMapContainingNoClientDataForClientId() throws NoAvailableDataException {
      String clientId = "randomIdNotInMap";

      clientService.getFunds(clientId);
   }

   @Test
   public void updateFunds_updatesFunds() throws NoAvailableDataException {
      Client returnClient1 = clientService.storeNewClient(createClient("", "userName1", 0));
      String clientId = returnClient1.getClientId();
      double initialFunds = returnClient1.getFunds();
      double fundsUpdate = initialFunds - 200;

      clientService.updateFunds(clientId, fundsUpdate);

      double returnedFunds = clientService.getFunds(clientId);
      assertThat(fundsUpdate, is(returnedFunds));
   }

   @Test(expected = NoAvailableDataException.class)
   public void updateFunds_handlesMapContainingNoClientDataForClientId() throws NoAvailableDataException {
      String clientId = "randomIdNotInMap";

      clientService.updateFunds(clientId, 900);
   }

   private Client createClient(String clientId, String userName, double funds) {
      return Client.builder()
         .clientId(clientId)
         .userName(userName)
         .funds(funds)
         .build();
   }
}