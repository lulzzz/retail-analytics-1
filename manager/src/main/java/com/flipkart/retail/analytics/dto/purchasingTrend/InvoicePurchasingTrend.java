package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoicePurchasingTrend extends PurchasingTrend {
    private String month;
    private String currency;
    private Long units;
    private Double amount;
}
