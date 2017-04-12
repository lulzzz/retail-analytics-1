package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.PurchasingTrend;

import java.util.List;

public interface AggregatedService {
    List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses);

    void getAggregatedOperationalPerformance(List<String> vendorSites, List<String> warehouses);
}
