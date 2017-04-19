package com.flipkart.retail.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregatedDetails {
    private String status;
    private String currency;
    private int units;
    private double amount;
}
