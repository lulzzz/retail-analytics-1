package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.*;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.repository.EntityRepository;
import lombok.RequiredArgsConstructor;

import javax.cache.annotation.CacheResult;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class BaseAggregationService {
    private final EntityRepository entityRepository;

    @CacheResult(cacheName = "purchasing_trend_cache")
    public AggregatedPurchasingTrendResponse getAggregatedPurchasingTrends(AggregatedPurchasingTrendRequest aggregatedPurchasingTrendRequest){
        AggregatedPurchasingTrendResponse aggregatedPurchasingTrendResponse = new AggregatedPurchasingTrendResponse();
        Map<EntityType, List<PurchasingTrend>> purchasingTrend = new HashMap<>();
        for (EntityType entityType : aggregatedPurchasingTrendRequest.getEntities()){
            AggregationService aggregationService = getEntityHandler(entityType);
            List<PurchasingTrend> purchasingTrends = aggregationService.getAggregatedPurchasingTrend
                    (aggregatedPurchasingTrendRequest.getVendorSites(), aggregatedPurchasingTrendRequest.getWarehouses());
            purchasingTrend.put(entityType, purchasingTrends);
        }
        aggregatedPurchasingTrendResponse.setPurchasingTrends(purchasingTrend);
        return aggregatedPurchasingTrendResponse;
    }

    @CacheResult(cacheName = "aggregated_details_cache")
    public AggregatedDetailedResponse getAggregatedDetails(AggregatedDetailedRequest aggregatedDetailedRequest){
        AggregatedDetailedResponse aggregatedDetailedResponse = new AggregatedDetailedResponse();
        Map<EntityType, AggregatedDetails> aggregatedDetails = new HashMap<>();
        for(EntityType entityType : aggregatedDetailedRequest.getEntities()){
            AggregationService aggregationService = getEntityHandler(entityType);
            AggregatedDetails aggregatedEntityDetails = aggregationService.getDetailedResponse
                    (aggregatedDetailedRequest.getVendorSites(), aggregatedDetailedRequest.getFromMonth(),
                            aggregatedDetailedRequest.getToMonth());
            aggregatedDetails.put(entityType, aggregatedEntityDetails);
        }
        aggregatedDetailedResponse.setAggregatedDetails(aggregatedDetails);
        return aggregatedDetailedResponse;
    }

    private AggregationService getEntityHandler(EntityType entityType){
        return entityRepository.getHandler(entityType);
    }
}
