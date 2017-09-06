package com.iggroup.universityworkshopmw.domain.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Client {

   private String clientId;
   private String userName;
   private double profitAndLoss;

   public Client(String clientId, String userName, double profitAndLoss) {
      this.clientId = clientId;
      this.userName = userName;
      this.profitAndLoss = profitAndLoss;
   }

   public void setProfitAndLoss(double profitAndLoss) {
      this.profitAndLoss = profitAndLoss;
   }
}
