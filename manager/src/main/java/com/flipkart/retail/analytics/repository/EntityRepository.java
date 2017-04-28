package com.flipkart.retail.analytics.repository;

import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.service.AggregationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityRepository {

    private final Map<EntityType, AggregationService> handlers = new HashMap<>();

    public void addHandler(EntityType entityType, AggregationService aggregationService){
        if(Objects.nonNull(entityType) && Objects.nonNull(aggregationService)){
            handlers.put(entityType, aggregationService);
        }
    }

    public AggregationService getHandler(EntityType entityType){
        return handlers.get(entityType);
    }
}
