package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.model.Market;
import com.iggroup.universityworkshopmw.domain.services.MarketDataService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iggroup.universityworkshopmw.TestHelper.APPLICATION_JSON_UTF8;
import static com.iggroup.universityworkshopmw.domain.enums.MarketName.GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MarketDataControllerTest {

   private MockMvc mockMvc;
   private MarketDataService marketDataService;
   private MarketDataController marketDataController;


   @Before
   public void setup() {
      marketDataService = mock(MarketDataService.class);
      marketDataController = new MarketDataController(marketDataService);
      mockMvc = MockMvcBuilders.standaloneSetup(marketDataController).build();
   }

   @Test
   public void getAllMarketData_returnsOkCodeAndListOfMarketInfo() throws Exception {
      //Given
      List<Market> listOfMarkets = new ArrayList<>();
      listOfMarkets.add(Market.builder()
            .marketId("market_1")
            .marketName(GOLD)
            .currentPrice(400.0)
            .build());
      when(marketDataService.getAllMarkets()).thenReturn(listOfMarkets);

      //When
      mockMvc.perform(get("/marketData/allMarkets"))
            //Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.[0].marketId", is("market_1")))
            .andExpect(jsonPath("$.[0].marketName", is(GOLD.getName())))
            .andExpect(jsonPath("$.[0].currentPrice", is(400.0)));
   }

   @Test
   public void getAllMarketData_handlesAnyException() throws Exception {
      //Given
      when(marketDataService.getAllMarkets()).thenThrow(new RuntimeException("Server exception!"));

      //When
      MvcResult mvcResult = mockMvc.perform(get("/marketData/allMarkets"))
            //Then
            .andExpect(status().isInternalServerError())
            .andReturn();

      String content = mvcResult.getResponse().getContentAsString();
      assertThat(content).isEqualTo("Something went wrong when retrieving all market data");
   }

   @Test
   public void getAllMarketPrices_returnsOkCodeAndMapOfMarketPrices() throws Exception {
      //Given
      Map<String, Double> marketIdToPriceMap = new HashMap<>();
      marketIdToPriceMap.put("market_1", 400.0);
      when(marketDataService.getMarketPrices()).thenReturn(marketIdToPriceMap);

      //When
      mockMvc.perform(get("/marketData/allPrices"))
            //Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.market_1", is(400.0)));
   }

   @Test
   public void getAllMarketPrices_handlesAnyException() throws Exception {
      //Given
      when(marketDataService.getMarketPrices()).thenThrow(new RuntimeException("Server exception!"));

      //When
      MvcResult mvcResult = mockMvc.perform(get("/marketData/allPrices"))
            //Then
            .andExpect(status().isInternalServerError())
            .andReturn();

      String content = mvcResult.getResponse().getContentAsString();
      assertThat(content).isEqualTo("Something went wrong when retrieving all market prices");
   }

}