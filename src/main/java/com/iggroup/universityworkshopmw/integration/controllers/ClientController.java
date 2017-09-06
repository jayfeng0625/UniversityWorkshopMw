package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.Client;
import com.iggroup.universityworkshopmw.domain.services.ClientService;
import com.iggroup.universityworkshopmw.integration.dto.ClientDto;
import com.iggroup.universityworkshopmw.integration.transformers.ClientTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

   private final ClientService clientService;

   public ClientController(ClientService clientService) {
      this.clientService = clientService;
   }

   @PostMapping("/createClient")
   public ResponseEntity<?> createClient(@RequestBody ClientDto clientDto) {
      try {
         Client clientTransformed = ClientTransformer.clientDtoToClientModel(clientDto);
         ClientDto responseBody = ClientTransformer.clientModelToClientDto(clientService.storeNewClient(clientTransformed));
         return new ResponseEntity<>(responseBody, HttpStatus.OK);

      } catch (Exception e) {
         log.info("Exception when creating new client, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when creating a new client", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @GetMapping("/profitAndLoss/{clientId}")
   public ResponseEntity<?> getClientProfitAndLoss(@PathVariable("clientId") String clientId) {
      try {
         double profitAndLoss = clientService.getProfitAndLoss(clientId);
         return new ResponseEntity<>(profitAndLoss, HttpStatus.OK);

      } catch (NoAvailableDataException e) {
         log.info("No available client data in clientIdToClientModelMap for clientId={}", clientId);
         return new ResponseEntity<>("No available client data for clientId=" + clientId, HttpStatus.NOT_FOUND);

      } catch (Exception e) {
         log.info("Exception when retrieving profit and loss, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when retrieving profit and loss", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}
