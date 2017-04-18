package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class POPurchasingTrend extends PurchasingTrend {
    private String currency;
    private int units;
    private double amount;

    public POPurchasingTrend(String month, String currency, int units, double amount){
        super(month);
        this.units = units;
        this.currency = currency;
        this.amount = amount;
    }
}
