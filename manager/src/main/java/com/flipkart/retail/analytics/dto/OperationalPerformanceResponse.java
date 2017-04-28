package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.MetricType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OperationalPerformanceResponse implements Serializable {
    private Map<MetricType, List<OperationalPerformance>> operationalPerformance;
    private Date lastUpdatedTime;
}
