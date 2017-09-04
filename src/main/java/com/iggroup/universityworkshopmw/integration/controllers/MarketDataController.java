package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.domain.services.MarketDataService;
import com.iggroup.universityworkshopmw.integration.dto.MarketDto;
import com.iggroup.universityworkshopmw.integration.transformers.MarketDataTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/marketData")
public class MarketDataController {

   private final MarketDataService marketDataService;

   public MarketDataController(MarketDataService marketDataService) {
      this.marketDataService = marketDataService;
   }

   @GetMapping("/allMarkets")
   public ResponseEntity<?> getAllMarketData() {
      try {
         List<Market> listOfMarkets = marketDataService.getAllMarkets();
         List<MarketDto> responseBody = MarketDataTransformer.transformMarketListToMarketDtoList(listOfMarkets);
         return new ResponseEntity<>(responseBody, HttpStatus.OK);

      } catch (Exception e) {
         log.info("Exception when retrieving all market data, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when retrieving all market data", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @GetMapping("/allPrices")
   public ResponseEntity<?> getAllMarketPrices() {
      try {
         Map<String, Double> responseBody = marketDataService.getMarketPrices();
         return new ResponseEntity<>(responseBody, HttpStatus.OK);

      } catch (Exception e) {
         log.info("Exception when retrieving all market prices, exceptionMessage={}", e);
         return new ResponseEntity<>("Something went wrong when retrieving all market prices", HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

}
