package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AggregatedPurchasingTrendResponse {
    private List<Map<EntityType, List<PurchasingTrend>>> purchasingTrends;
    private Date lastLoginTime;
}
