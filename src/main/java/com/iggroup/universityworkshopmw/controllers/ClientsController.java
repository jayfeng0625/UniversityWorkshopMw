package com.iggroup.universityworkshopmw.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientsController {

   @RequestMapping(value = "/getClient", method = RequestMethod.GET)
   public String custom() {
      return "getClient";
   }
}
