package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.*;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.repository.EntityRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BaseAggregationService {
    private final EntityRepository entityRepository;

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

    public AggregatedDetailedResponse getAggregatedDetails(AggregatedDetailedRequest aggregatedDetailedRequest){
        AggregatedDetailedResponse aggregatedDetailedResponse = new AggregatedDetailedResponse();
        List<Map<EntityType, List<AggregatedDetails>>> aggregatedDetailsList = new ArrayList<>();
        for(EntityType entityType : aggregatedDetailedRequest.getEntities()){
            AggregationService aggregationService = getEntityHandler(entityType);
            List<AggregatedDetails> aggregatedDetails = aggregationService.getDetailedResponse
                    (aggregatedDetailedRequest.getVendorSites(), aggregatedDetailedRequest.getFromMonth(),
                            aggregatedDetailedRequest.getToMonth());
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
}
