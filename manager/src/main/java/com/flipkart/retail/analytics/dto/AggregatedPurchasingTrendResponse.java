package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AggregatedPurchasingTrendResponse implements Serializable {
    private Map<EntityType, List<PurchasingTrend>> purchasingTrends;
    private Date lastUpdatedTime;
}
