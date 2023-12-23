package com.kredx.cashsettlement.transaction.svc.impl.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lender;
    private String borrower;
    private double amount;
    private Date date;

    // getters and setters

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLender() {
        return lender;
    }

    public Transaction setLender(String lender) {
        this.lender = lender;
        return this;
    }

    public String getBorrower() {
        return borrower;
    }

    public Transaction setBorrower(String borrower) {
        this.borrower = borrower;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Transaction setDate(Date date) {
        this.date = date;
        return this;
    }


//    @PrePersist
//    protected void onCreate() {
//        date = new Date();
//    }
}