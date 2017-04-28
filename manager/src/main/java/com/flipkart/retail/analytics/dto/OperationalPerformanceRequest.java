package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.MetricType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OperationalPerformanceRequest implements Serializable {
    private List<String> vendorSites;
    private List<String> warehouses;
    private List<MetricType> metrics;
}
