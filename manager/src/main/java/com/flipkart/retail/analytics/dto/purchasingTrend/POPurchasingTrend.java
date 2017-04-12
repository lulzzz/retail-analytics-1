package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class POPurchasingTrend extends PurchasingTrend {
    private int units;
    private double amount;

    public POPurchasingTrend(String month, int units, double amount){
        this.month = month;
        this.units = units;
        this.amount = amount;
    }
}
