package com.iggroup.universityworkshopmw.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No available market price for market requested.")  // 400
public class NoMarketPriceAvailableException extends Exception {

   public NoMarketPriceAvailableException(String message) {
      super(message);
   }

}
