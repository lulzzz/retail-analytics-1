package com.flipkart.retail.analytics.dto.purchasingTrend;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import lombok.Data;

@Data
public class QCPurchasingTrend extends PurchasingTrend {
    private String currency;
    private int rejectedUnits;
    private double rejectedAmount;
    private int excessUnits;
    private double excessAmount;

    public QCPurchasingTrend(String month, String currency, int rejectedUnits, double rejectedAmount, int excessUnits,
                             double excessAmount){
        super(month);
        this.currency = currency;
        this.rejectedUnits = rejectedUnits;
        this.rejectedAmount = rejectedAmount;
        this.excessAmount = excessUnits;
        this.excessAmount = excessAmount;
    }
}
