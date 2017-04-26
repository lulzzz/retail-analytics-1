package com.flipkart.retail.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public abstract class PurchasingTrend implements Serializable {
    protected String month;
}
