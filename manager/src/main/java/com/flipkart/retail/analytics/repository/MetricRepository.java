package com.flipkart.retail.analytics.repository;

import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.service.AggregationService;

import java.util.HashMap;
import java.util.Objects;


public class MetricRepository extends HashMap<MetricType, AggregationService>  {
    public void addHandler(MetricType metricType, AggregationService aggregationService){
        if(Objects.nonNull(metricType) && Objects.nonNull(aggregationService)){
            this.put(metricType, aggregationService);
        }
    }

    public AggregationService getHandler(MetricType metricType){
        return this.get(metricType);
    }
}
