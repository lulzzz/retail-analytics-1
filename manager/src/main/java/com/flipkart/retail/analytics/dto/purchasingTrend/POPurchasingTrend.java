package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class POPurchasingTrend extends PurchasingTrend {
    private String month;
    private String currency;
    private Long deliveredUnits;
    private Long totalUnits;
    private Double deliveredAmount;
    private Double totalAmount;
}
