package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.exceptions.InsufficientFundsException;
import com.iggroup.universityworkshopmw.domain.exceptions.NoAvailableDataException;
import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import com.iggroup.universityworkshopmw.domain.services.OpenPositionsService;
import com.iggroup.universityworkshopmw.integration.dto.OpenPositionDto;
import com.iggroup.universityworkshopmw.integration.transformers.OpenPositionDtoTransformer;
import com.iggroup.universityworkshopmw.integration.transformers.OpenPositionTransformer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(value = "/openPositions", description = "Operations relating to open positions")
@Slf4j
@RestController
@RequestMapping(value = "/openPositions", produces = "application/json;charset=UTF-8", consumes = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class OpenPositionsController {
   private final OpenPositionsService openPositionsService;

   @ApiOperation(value = "Get all open positions for client",
      notes = "Returns all open positions for a given clientId",
      response = OpenPositionDto.class,
      responseContainer = "List")
   @GetMapping("/{clientId}")
   public ResponseEntity<?> getOpenPositions(@PathVariable("clientId") String clientId) {
      try {
         List<OpenPosition> openPositions = openPositionsService.getOpenPositionsForClient(clientId);
         List<OpenPositionDto> responseBody = OpenPositionDtoTransformer.transform(openPositions);

         return new ResponseEntity<>(responseBody, OK);
      } catch (NoAvailableDataException e) {
         log.info("No open positions for client={}, ", clientId, e);
         return new ResponseEntity<>("No open positions were available for client: " + clientId, BAD_REQUEST);
      } catch (Exception e) {
         log.info("Could not retrieve open positions for client={}, ", clientId, e);
         return new ResponseEntity<>("Something went wrong while getting client positions", INTERNAL_SERVER_ERROR);
      }
   }

   @ApiOperation(value =  "Create an open position",
      notes = "Create a new position for a given client",
      response = String.class)
   @PostMapping("/{clientId}")
   public ResponseEntity<?> addOpenPosition(@PathVariable("clientId") String clientId,
                                            @RequestBody OpenPositionDto openPositionDto) {
      try {
         OpenPosition openPosition = OpenPositionTransformer.transform(openPositionDto);
         OpenPosition openPositionWithId = openPositionsService.addOpenPositionForClient(clientId, openPosition);
         Map<String, String> headers = newHashMap();
         headers.put("openPositionId", openPositionWithId.getId());

         return new ResponseEntity<>(headers, OK);
      } catch (InsufficientFundsException e) {
         log.info("Client={} lacked sufficient funds to trade, ", clientId, e);
         return new ResponseEntity<>("Client: " + clientId + " lacked sufficient funds to trade", BAD_REQUEST);
      } catch (NoAvailableDataException e) {
         log.info("Could not open position for client={}, ", clientId, e);
         return new ResponseEntity<>("Could not open position for clientId: " + clientId, BAD_REQUEST);
      } catch (Exception e) {
         log.info("Could not add an open position for client={}, ", clientId, e);
         return new ResponseEntity<>("Something went wrong when opening a position", INTERNAL_SERVER_ERROR);
      }
   }

   @ApiOperation(value = "Delete an open position",
      notes = "Deletes an open position for a given client. Responds with closing profit and loss",
      response = Double.class)
   @PostMapping("/{clientId}/{openPositionId}/{closingPrice}")
   public ResponseEntity<?> closeOpenPosition(@PathVariable("clientId") String clientId,
                                              @PathVariable("openPositionId") String openPositionId,
                                              @PathVariable("closingPrice") String closingPrice) {
      try {
         Double response = openPositionsService.closeOpenPosition(clientId, openPositionId, Double.parseDouble(closingPrice));
         return new ResponseEntity<>(response, OK);
      } catch (NoAvailableDataException e) {
         log.info("Client={} had no open positions, or no positions matching clientId={}, ", clientId, openPositionId, e);
         return new ResponseEntity<>("Client: " + clientId + " had no open positions or no positions matching clientId: " + openPositionId, BAD_REQUEST);
      } catch (Exception e) {
         log.info("Could not delete position={}, for client={}, at price={}, ", openPositionId, clientId, closingPrice, e);
         return new ResponseEntity<>("Could not close position: " + openPositionId + " for client: " + clientId + " at price: " + closingPrice, INTERNAL_SERVER_ERROR);
      }
   }
}
