package com.iggroup.universityworkshopmw.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Client does not have funds to trade.")  // 400
public class InsufficientFundsException extends RuntimeException {

   public InsufficientFundsException(String message) {
      super(message);
   }

}
