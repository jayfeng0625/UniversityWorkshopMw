package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.helpers.Helper;
import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ClientService {

   private Map<String, Client> clientIdToClientModelMap = new ConcurrentHashMap<>();
   private final String ID_PREFIX = "client_";
   private final double INITIAL_PROFIT_LOSS = 10000; //10,000

   public Client storeNewClient(Client client) {
      String uniqueId = Helper.createUniqueId(ID_PREFIX);
      Client enrichedClient = Client.builder()
         .clientId(uniqueId)
         .userName(client.getUserName())
         .profitAndLoss(INITIAL_PROFIT_LOSS)
         .build();
      clientIdToClientModelMap.put(uniqueId, enrichedClient);
      log.info("Added new client={}", enrichedClient);
      return enrichedClient;
   }

   public double getProfitAndLoss(String clientId) throws NoAvailableDataException {
      Client client = getClientDataFromMap(clientId);
      double profitAndLoss = client.getProfitAndLoss();
      log.info("Retrieving profitAndLoss={} for clientId={}", profitAndLoss, clientId);
      return profitAndLoss;
   }

   protected void updateProfitAndLoss(String clientId, double updatedProfitAndLoss) throws NoAvailableDataException {
      Client client = getClientDataFromMap(clientId);
      log.info("Updating updatedProfitAndLoss for clientId={}, oldProfitAndLoss={}, updatedProfitAndLoss={}", clientId, client.getProfitAndLoss(), updatedProfitAndLoss);
      client.setProfitAndLoss(updatedProfitAndLoss);
      clientIdToClientModelMap.put(clientId, client);
   }

   private Client getClientDataFromMap(String clientId) throws NoAvailableDataException {
      if (clientIdToClientModelMap.containsKey(clientId)) {
         return clientIdToClientModelMap.get(clientId);
      } else {
         throw new NoAvailableDataException("No available client data in clientIdToClientModelMap for clientId=" + clientId);
      }
   }
}
