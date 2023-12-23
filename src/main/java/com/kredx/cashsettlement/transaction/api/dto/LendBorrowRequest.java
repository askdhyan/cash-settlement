package com.kredx.cashsettlement.transaction.api.dto;

import jakarta.validation.constraints.Size;

import java.util.Date;

public class LendBorrowRequest
{

    @Size(min = 1, max = 20, message = "username should not be empty and size between 1 to 20")
    private String username;

    @Size(min = 1)
    private double amount;

    private Date date;

    public String getUsername() {
        return username;
    }

    public LendBorrowRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public LendBorrowRequest setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public LendBorrowRequest setDate(Date date) {
        this.date = date;
        return this;
    }
}
