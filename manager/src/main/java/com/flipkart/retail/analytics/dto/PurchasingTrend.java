package com.flipkart.retail.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class PurchasingTrend {
    protected String month;
}
