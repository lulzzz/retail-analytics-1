package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QCPurchasingTrend extends PurchasingTrend {
    private String month;
    private String currency;
    private Long rejectedUnits;
    private Double rejectedAmount;
    private Long excessUnits;
    private Double excessAmount;
}
