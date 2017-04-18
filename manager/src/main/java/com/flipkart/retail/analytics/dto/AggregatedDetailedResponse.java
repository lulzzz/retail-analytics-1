package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AggregatedDetailedResponse {
    private List<Map<EntityType, List<AggregatedDetails>>> aggregatedDetails;
}
