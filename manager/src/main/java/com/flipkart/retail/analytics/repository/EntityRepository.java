package com.flipkart.retail.analytics.repository;

import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.service.AggregationService;

import java.util.HashMap;
import java.util.Objects;

public class EntityRepository extends HashMap<EntityType, AggregationService>{

    public void addHandler(EntityType entityType, AggregationService aggregationService){
        if(Objects.nonNull(entityType) && Objects.nonNull(aggregationService)){
            this.put(entityType, aggregationService);
        }
    }

    public AggregationService getHandler(EntityType entityType){
        return this.get(entityType);
    }
}
