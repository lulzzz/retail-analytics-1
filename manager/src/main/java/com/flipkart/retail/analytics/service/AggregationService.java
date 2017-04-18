package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;

import java.util.List;

public interface AggregationService {
    List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses);

    void getAggregatedOperationalPerformance(List<String> vendorSites, List<String> warehouses);

    List<AggregatedDetails> getDetailedResponse(List<String> vendorSites);
}
