package com.flipkart.retail.analytics.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseOrder {
    private String fsn;
    private String status;
    private String month;
    private Integer quantity;
    private String currency;
    private Double amount;

    public PurchaseOrder(String month, String currency, Integer quantity, Double amount){
        this.month = month;
        this.currency = currency;
        this.quantity = quantity;
        this.amount = amount;
    }
}
