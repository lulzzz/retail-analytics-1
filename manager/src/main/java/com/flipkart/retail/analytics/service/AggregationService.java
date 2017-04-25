package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;

import java.util.List;

public interface AggregationService {
    List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses);

    AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth);
}
