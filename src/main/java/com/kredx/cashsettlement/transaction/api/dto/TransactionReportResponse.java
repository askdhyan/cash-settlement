package com.kredx.cashsettlement.transaction.api.dto;

import lombok.Data;

@Data
public class TransactionReportResponse {

    String username;
    double balance;

    public TransactionReportResponse() {
    }

    public TransactionReportResponse(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public TransactionReportResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public double getBalance() {
        return balance;
    }

    public TransactionReportResponse setBalance(double balance) {
        this.balance = balance;
        return this;
    }
}
