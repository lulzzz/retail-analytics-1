package com.flipkart.retail.analytics.persistence.entity;

import lombok.Data;

@Data
public class ReturnOrder {
    private String status;
    private String month;
    private Integer quantity;
    private String currency;
    private Double amount;

    public ReturnOrder(String month, String currency, Integer quantity, Double amount){
        this.month = month;
        this.currency = currency;
        this.quantity = quantity;
        this.amount = amount;
    }
}
