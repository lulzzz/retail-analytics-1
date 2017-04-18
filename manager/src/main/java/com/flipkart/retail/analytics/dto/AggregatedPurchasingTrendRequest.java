package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import java.util.List;

@Data
public class AggregatedPurchasingTrendRequest {
    private List<EntityType> entities;
    private List<String> vendorSites;
    private List<String> warehouses;
}
