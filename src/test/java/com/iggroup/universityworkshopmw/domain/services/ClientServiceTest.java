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

      assertNotNull(returnClient1.getId());
      assertNotNull(returnClient2.getId());
      assertNotEquals(returnClient1.getId(), returnClient2.getId());
   }

   @Test
   public void getProfitAndLoss_getsProfitAndLossForClientId() throws NoAvailableDataException {
      Client returnClient1 = clientService.storeNewClient(createClient("", "userName1", 0));
      String id = returnClient1.getId();
      double expectedProfitAndLoss = returnClient1.getProfitAndLoss();

      double actualProfitAndLoss = clientService.getProfitAndLoss(id);

      assertThat(expectedProfitAndLoss, is(actualProfitAndLoss));
   }

   @Test(expected = NoAvailableDataException.class)
   public void getProfitAndLoss_handlesMapContainingNoClientDataForClientId() throws NoAvailableDataException {
      String id = "randomIdNotInMap";

      clientService.getProfitAndLoss(id);
   }

   @Test
   public void updateProfitAndLoss_updatesProfitAndLoss() throws NoAvailableDataException {
      Client returnClient1 = clientService.storeNewClient(createClient("", "userName1", 0));
      String id = returnClient1.getId();
      double initialProfitAndLoss = returnClient1.getProfitAndLoss();
      double profitAndLossUpdate = initialProfitAndLoss - 200;

      clientService.updateProfitAndLoss(id, profitAndLossUpdate);

      double returnedProfitAndLoss = clientService.getProfitAndLoss(id);
      assertThat(profitAndLossUpdate, is(returnedProfitAndLoss));
   }

   @Test(expected = NoAvailableDataException.class)
   public void updateProfitAndLoss_handlesMapContainingNoClientDataForClientId() throws NoAvailableDataException {
      String id = "randomIdNotInMap";

      clientService.updateProfitAndLoss(id, 900);
   }

   private Client createClient(String id, String userName, double profitAndLoss) {
      return Client.builder()
         .id(id)
         .userName(userName)
         .profitAndLoss(profitAndLoss)
         .build();
   }
}