package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.*;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.repository.EntityRepository;
import com.flipkart.retail.analytics.repository.MetricRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BaseAggregationService {
    private final EntityRepository entityRepository;
    private final MetricRepository metricRepository;

    public AggregatedPurchasingTrendResponse getAggregatedPurchasingTrends(List<EntityType> entityTypes, List<String> vendorSites, List<String> warehouses){
        AggregatedPurchasingTrendResponse aggregatedPurchasingTrendResponse = new AggregatedPurchasingTrendResponse();
        List<Map<EntityType, List<PurchasingTrend>>> purchasingTrendsList = new ArrayList<>();
        for (EntityType entityType : entityTypes){
            AggregationService aggregationService = getEntityHandler(entityType);
            List<PurchasingTrend> purchasingTrends = aggregationService.getAggregatedPurchasingTrend(vendorSites, warehouses);
            purchasingTrendsList.add(new HashMap<EntityType, List<PurchasingTrend>>(){{
                put(entityType, purchasingTrends);
            }});
        }
        aggregatedPurchasingTrendResponse.setPurchasingTrends(purchasingTrendsList);
        return aggregatedPurchasingTrendResponse;
    }

    public OperationalPerformanceResponse getAggregatedOperationalPerformance(OperationalPerformanceRequest operationalPerformanceRequest){
        OperationalPerformanceResponse operationalPerformanceResponse = new OperationalPerformanceResponse();
        List<Map<MetricType, List<OperationalPerformance>>> operationalPerformanceList = new ArrayList<>();
        for (MetricType metricType : operationalPerformanceRequest.getMetrics()) {
            AggregationService aggregationService = getMetricHandler(metricType);
            List<OperationalPerformance> operationalPerformances = aggregationService.getAggregatedOperationalPerformance(metricType,
                    operationalPerformanceRequest.getVendorSites(), operationalPerformanceRequest.getWarehouses());
            operationalPerformanceList.add(new HashMap<MetricType, List<OperationalPerformance>>(){{
                put(metricType, operationalPerformances);
            }});

        }
        operationalPerformanceResponse.setOperationalPerformance(operationalPerformanceList);
        return operationalPerformanceResponse;
    }

    public AggregatedDetailedResponse getAggregatedDetails(AggregatedDetailedRequest aggregatedDetailedRequest){
        AggregatedDetailedResponse aggregatedDetailedResponse = new AggregatedDetailedResponse();
        List<Map<EntityType, List<AggregatedDetails>>> aggregatedDetailsList = new ArrayList<>();
        for(EntityType entityType : aggregatedDetailedRequest.getEntities()){
            AggregationService aggregationService = getEntityHandler(entityType);
            List<AggregatedDetails> aggregatedDetails = aggregationService.getDetailedResponse
                    (aggregatedDetailedRequest.getVendorSites());
            aggregatedDetailsList.add(new HashMap<EntityType, List<AggregatedDetails>>(){{
                put(entityType, aggregatedDetails);
            }});
        }
        aggregatedDetailedResponse.setAggregatedDetails(aggregatedDetailsList);
        return aggregatedDetailedResponse;
    }

    private AggregationService getEntityHandler(EntityType entityType){
        return entityRepository.getHandler(entityType);
    }

    private AggregationService getMetricHandler(MetricType metricType){
        return metricRepository.getHandler(metricType);
    }
}
