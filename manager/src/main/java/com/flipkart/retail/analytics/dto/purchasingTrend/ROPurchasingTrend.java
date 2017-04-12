package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class ROPurchasingTrend extends PurchasingTrend {
    private String currency;
    private int units;
    private double amount;

    public ROPurchasingTrend(String month, String currency, int units, double amount){
        this.month = month;
        this.currency = currency;
        this.units = units;
        this.amount = amount;
    }
}
