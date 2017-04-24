package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.ROAggregatedDetails;
import com.flipkart.retail.analytics.dto.purchasingTrend.ROPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.ReturnOrderManager;
import com.flipkart.retail.analytics.persistence.entity.ReturnOrder;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.RETURN_ORDER)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ReturnOrderService implements AggregationService {
    private final ReturnOrderManager returnOrderManager;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        List<ReturnOrder> returnOrders = returnOrderManager.getReturnOrders(getROTable(), vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (ReturnOrder returnOrder : returnOrders){
            ROPurchasingTrend roPurchasingTrend = new ROPurchasingTrend(returnOrder.getMonth(), returnOrder.getCurrency(),
                    returnOrder.getQuantity(), returnOrder.getAmount());
            purchasingTrends.add(roPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        List<ROAggregatedDetails> returnOrderList = returnOrderManager.getReturnOrderDetails(getROTable(), vendorSites);
        List<AggregatedDetails> aggregatedDetailsList = new ArrayList<>();
        for(ROAggregatedDetails roAggregatedDetails : returnOrderList){
            aggregatedDetailsList.add(roAggregatedDetails);
        }
        return aggregatedDetailsList;
    }

    private String getROTable(){
        return retailAnalyticsUtils.getTableName(reportsConfiguration.getReturnOrder());
    }
}
