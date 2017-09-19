package com.iggroup.universityworkshopmw.domain.services;

import com.iggroup.universityworkshopmw.domain.exceptions.InsufficientFundsException;
import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.collect.Lists.newArrayList;

@Slf4j
@Component
public class OpenPositionsService {
   private final ClientService clientService;
   private ConcurrentMap<String, List<OpenPosition>> clientPositionStore;

   public OpenPositionsService(ClientService clientService) {
      this.clientService = clientService;
      clientPositionStore = new ConcurrentHashMap<>();

   }

   public List<OpenPosition> getOpenPositionsForClient(String clientId) throws NoAvailableDataException {
      List<OpenPosition> openPositions = clientPositionStore.get(clientId);

      if (openPositions != null) {
         return openPositions;
      }
      throw new NoAvailableDataException("No open positions exist for client: " + clientId);
   }

   public void addOpenPositionForClient(String clientId, OpenPosition newOpenPosition) throws NoAvailableDataException, InsufficientFundsException {
      double positionPrice = newOpenPosition.getBuySize() * newOpenPosition.getOpeningPrice();
      List<OpenPosition> openPositionsForClient = clientPositionStore.get(clientId);

      double clientFunds = checkClientFunds(clientId, positionPrice);
      updateStoreWithNewPosition(clientId, newOpenPosition, openPositionsForClient);

      clientService.updateProfitAndLoss(clientId, clientFunds);
   }

   public Double closeOpenPosition(String clientId, String openPosition, Double closingPrice) throws NoAvailableDataException {
      List<OpenPosition> openPositions = clientPositionStore.get(clientId);
      if (clientId == null) {
         throw new NoAvailableDataException("Client had no positions");
      }

      OpenPosition position = openPositions.stream()
         .filter(pos -> pos.getId().equals(openPosition))
         .findFirst()
         .orElseThrow(() -> new NoAvailableDataException("No position exists with id: " + openPosition));

      Double closingProfitAndLoss = getNewProfitAndLoss(closingPrice, position.getOpeningPrice(), position.getBuySize());
      openPositions.remove(position);
      if (openPositions.isEmpty()) {
         clientPositionStore.remove(clientId);
      }
      return closingProfitAndLoss;
   }

   public void updateMarketValue(String marketId, Double newValue) {
      clientPositionStore.keySet().stream()
         .forEach(client -> {
            List<OpenPosition> openPositions = clientPositionStore.get(client);
            openPositions.stream()
               .forEach(openPosition -> updateProfitAndLoss(marketId, newValue, openPosition, openPositions));
         });
   }

   private void updateProfitAndLoss(String marketId, Double newValue, OpenPosition openPosition, List<OpenPosition> openPositions) {
      if (openPosition.getMarketId().equals(marketId)) {
         openPositions.set(openPositions.indexOf(openPosition), createNewPosition(openPosition, newValue));
      }
   }

   private OpenPosition createNewPosition(OpenPosition openPosition, Double newValue) {
      return OpenPosition.builder()
         .id(openPosition.getId())
         .marketId(openPosition.getMarketId())
         .buySize(openPosition.getBuySize())
         .openingPrice(openPosition.getOpeningPrice())
         .profitAndLoss(getNewProfitAndLoss(newValue, openPosition.getOpeningPrice(), openPosition.getBuySize()))
         .build();
   }

   private Double getNewProfitAndLoss(Double newValue, Double openingPrice, Integer buySize) {
      return (newValue - openingPrice) * buySize;
   }

   private double checkClientFunds(String clientId, double positionPrice) throws NoAvailableDataException {
      Client client = clientService.getClientDataFromMap(clientId);

      if (client.getProfitAndLoss() < positionPrice) {
         throw new InsufficientFundsException("Client: " + clientId + " lacks sufficient funds to place that trade");
      }
      return client.getProfitAndLoss();
   }

   private void updateStoreWithNewPosition(String clientId, OpenPosition newOpenPosition, List<OpenPosition> openPositionsForClient) {
      if (openPositionsForClient != null) {
         openPositionsForClient.add(newOpenPosition);
      } else {
         clientPositionStore.put(clientId, newArrayList(newOpenPosition));
      }
   }
}
