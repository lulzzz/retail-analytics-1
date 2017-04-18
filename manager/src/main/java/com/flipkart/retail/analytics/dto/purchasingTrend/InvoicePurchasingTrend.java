package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class InvoicePurchasingTrend extends PurchasingTrend {
    private String currency;
    private int units;
    private double amount;

    public InvoicePurchasingTrend(String month, String currency, int units, double amount){
        super(month);
        this.units = units;
        this.currency = currency;
        this.amount = amount;
    }
}
