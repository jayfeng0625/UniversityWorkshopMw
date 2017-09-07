package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.domain.services.MarketDataService;
import com.iggroup.universityworkshopmw.integration.dto.MarketDto;
import com.iggroup.universityworkshopmw.integration.transformers.MarketDataTransformer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Api(value = "/marketData", description = "Operations relating to market data")
@Slf4j
@RestController
@RequestMapping("/marketData")
@RequiredArgsConstructor
public class MarketDataController {

   private final MarketDataService marketDataService;

   @ApiOperation(value = "Get all market data",
         notes = "Returns a list of markets")
   @GetMapping("/allMarkets")
   public ResponseEntity<?> getAllMarketData() {
      try {
         List<Market> listOfMarkets = marketDataService.getAllMarkets();
         List<MarketDto> responseBody = MarketDataTransformer.transformMarketListToMarketDtoList(listOfMarkets);
         return new ResponseEntity<>(responseBody, OK);

      } catch (Exception e) {
         log.info("Exception when retrieving all market data, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when retrieving all market data", INTERNAL_SERVER_ERROR);
      }
   }

   @ApiOperation(value = "Get all market prices",
         notes = "Returns a map consisting of marketId and current market price")
   @GetMapping("/allPrices")
   public ResponseEntity<?> getAllMarketPrices() {
      try {
         Map<String, Double> responseBody = marketDataService.getMarketPrices();
         return new ResponseEntity<>(responseBody, OK);

      } catch (Exception e) {
         log.info("Exception when retrieving all market prices, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when retrieving all market prices", INTERNAL_SERVER_ERROR);
      }
   }

}
