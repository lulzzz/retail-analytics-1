package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ROPurchasingTrend extends PurchasingTrend {
    private String month;
    private String currency;
    private int units;
    private double amount;
}
