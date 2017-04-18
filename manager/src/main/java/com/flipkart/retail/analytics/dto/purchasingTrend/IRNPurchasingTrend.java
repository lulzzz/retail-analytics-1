package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class IRNPurchasingTrend extends PurchasingTrend {
    private int units;
    private double amount;

    public IRNPurchasingTrend(String month, int units, double amount){
        super(month);
        this.units = units;
        this.amount = amount;
    }
}
