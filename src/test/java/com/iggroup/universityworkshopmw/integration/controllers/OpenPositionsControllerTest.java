package com.iggroup.universityworkshopmw.integration.controllers;

import com.iggroup.universityworkshopmw.domain.model.OpenPosition;
import com.iggroup.universityworkshopmw.domain.services.OpenPositionsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OpenPositionsControllerTest {

   private MockMvc mockMvc;
   private OpenPositionsController openPositionsController;
   private OpenPositionsService openPositionsService;

   @Before
   public void setUp() {
      openPositionsService = mock(OpenPositionsService.class);
      openPositionsController = new OpenPositionsController(openPositionsService);
      mockMvc = MockMvcBuilders.standaloneSetup(openPositionsController).build();
   }

   @Test
   public void successfullyGetsOpenPositionsForClient() throws Exception {
      List<OpenPosition> openPositions = createOpenPositions();
      when(openPositionsService.getOpenPositionsForClient("client_12345")).thenReturn(openPositions);

      MvcResult result = mockMvc.perform(get("/openPositions/client_12345")
         .contentType(APPLICATION_JSON))
         .andExpect(status().isOk()).andReturn();

      String content = result.getResponse().getContentAsString();
      assertEquals(content, "[{\"id\":\"open_position_id\",\"marketId\":\"market_id\",\"profitAndLoss\":1000.0,\"openingPrice\":150.0,\"buySize\":100}]");

      ArgumentCaptor<String> clientIdCaptor = forClass(String.class);
      verify(openPositionsService, times(1)).getOpenPositionsForClient(clientIdCaptor.capture());
      verifyNoMoreInteractions(openPositionsService);

      String clientId = clientIdCaptor.getValue();
      assertThat(clientId, is("client_12345"));
   }

   @Test
   public void returnsInternalServerErrorForFailedRequest() throws Exception {
      when(openPositionsService.getOpenPositionsForClient("client_12345")).thenThrow(new RuntimeException("Internal server error"));

      MvcResult result = mockMvc.perform(get("/openPositions/client_12345")
         .contentType(APPLICATION_JSON))
         .andExpect(status().isInternalServerError()).andReturn();

      String content = result.getResponse().getContentAsString();
      assertEquals(content, "Something went wrong while getting client positions");

   }

   private List<OpenPosition> createOpenPositions() {
      return newArrayList(createOpenPosition());
   }

   private OpenPosition createOpenPosition() {
      return OpenPosition.builder()
         .id("open_position_id")
         .marketId("market_id")
         .profitAndLoss(1000.00)
         .openingPrice(150.00)
         .buySize(100)
         .build();
   }
}
