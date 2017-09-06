package com.iggroup.universityworkshopmw.integration.dto;

public class ClientDto {

   private String clientId;
   private String userName;
   private Double profitAndLoss;

   public ClientDto() {
   }

   public ClientDto(String clientId, String userName, Double profitAndLoss) {
      this.clientId = clientId;
      this.userName = userName;
      this.profitAndLoss = profitAndLoss;
   }

   public String getClientId() {
      return clientId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public Double getProfitAndLoss() {
      return profitAndLoss;
   }

   public void setProfitAndLoss(Double profitAndLoss) {
      this.profitAndLoss = profitAndLoss;
   }
}
