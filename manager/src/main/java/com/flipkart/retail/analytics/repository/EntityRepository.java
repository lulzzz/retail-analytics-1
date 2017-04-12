package com.flipkart.retail.analytics.repository;

import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.service.AggregatedService;

import java.util.HashMap;
import java.util.Objects;

public class EntityRepository extends HashMap<EntityType, AggregatedService>{

    public void addHandler(EntityType entityType, AggregatedService aggregatedService){
        if(Objects.nonNull(entityType) && Objects.nonNull(aggregatedService)){
            this.put(entityType, aggregatedService);
        }
    }

    public AggregatedService getHandler(EntityType entityType){
        return this.get(entityType);
    }
}
