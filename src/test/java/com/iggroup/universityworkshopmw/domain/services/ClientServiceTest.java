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
   public void shouldAssignUniqueIdPerAddition() {
      //Given
      Client client1 = new Client("", "userName1", 0);
      Client client2 = new Client("", "userName2", 0);

      //When
      Client returnClient1 = clientService.storeNewClient(client1);
      Client returnClient2 = clientService.storeNewClient(client2);

      //Then
      assertNotNull(returnClient1.getClientId());
      assertNotNull(returnClient2.getClientId());
      assertNotEquals(returnClient1.getClientId(), returnClient2.getClientId());
   }

   @Test
   public void shouldGetProfitAndLossForClientId() throws NoAvailableDataException {
      //Given
      Client returnClient1 = clientService.storeNewClient(new Client("", "userName1", 0));
      String clientId = returnClient1.getClientId();
      double expectedProfitAndLoss = returnClient1.getProfitAndLoss();

      //When
      double actualProfitAndLoss = clientService.getProfitAndLoss(clientId);

      //Then
      assertThat(expectedProfitAndLoss, is(actualProfitAndLoss));
   }

   @Test(expected = NoAvailableDataException.class)
   public void shouldHandleMapContainingNoClientDataForRetrievalOfProfitAndLoss() throws NoAvailableDataException {
      //Given
      String clientId = "randomIdNotInMap";

      //When
      clientService.getProfitAndLoss(clientId);
   }

   @Test
   public void shouldUpdateProfitAndLoss() throws NoAvailableDataException {
      //Given
      Client returnClient1 = clientService.storeNewClient(new Client("", "userName1", 0));
      String clientId = returnClient1.getClientId();
      double initialProfitAndLoss = returnClient1.getProfitAndLoss();
      double profitAndLossUpdate = initialProfitAndLoss - 200;

      //When
      clientService.updateProfitAndLoss(clientId, profitAndLossUpdate);

      //Then
      double returnedProfitAndLoss = clientService.getProfitAndLoss(clientId);
      assertThat(profitAndLossUpdate, is(returnedProfitAndLoss));
   }

   @Test(expected = NoAvailableDataException.class)
   public void shouldHandleMapContainingNoClientDataForProfitAndLossUpdate() throws NoAvailableDataException {
      //Given
      String clientId = "randomIdNotInMap";

      //When
      clientService.updateProfitAndLoss(clientId, 900);
   }

}