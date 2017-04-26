package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class AggregatedDetailedResponse implements Serializable {
    private Map<EntityType, AggregatedDetails> aggregatedDetails;
}
