package com.flipkart.retail.analytics.service;

import com.flipkart.retail.analytics.dto.AggregatedPurchasingTrendResponse;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
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
            AggregatedService aggregatedService = getEntityHandler(entityType);
            List<PurchasingTrend> purchasingTrends = aggregatedService.getAggregatedPurchasingTrend(vendorSites, warehouses);
            purchasingTrendsList.add(new HashMap<EntityType, List<PurchasingTrend>>(){{
                put(entityType, purchasingTrends);
            }});
        }
        aggregatedPurchasingTrendResponse.setPurchasingTrends(purchasingTrendsList);
        return aggregatedPurchasingTrendResponse;
    }

    private AggregatedService getEntityHandler(EntityType entityType){
        return entityRepository.getHandler(entityType);
    }
}
