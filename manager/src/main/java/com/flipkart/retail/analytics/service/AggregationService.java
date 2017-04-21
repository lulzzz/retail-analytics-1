package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.enums.MetricType;

import java.util.List;

public interface AggregationService {
    List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses);

    List<OperationalPerformance> getAggregatedOperationalPerformance(MetricType metricType, List<String> vendorSites, List<String> warehouses);

    List<AggregatedDetails> getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth);
}
