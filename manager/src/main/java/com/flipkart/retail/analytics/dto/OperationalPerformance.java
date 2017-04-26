package com.flipkart.retail.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OperationalPerformance implements Serializable {
    private String month;
    private String value;
}
