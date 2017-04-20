package com.flipkart.retail.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationalPerformance {
    private String month;
    private Double value;
}
