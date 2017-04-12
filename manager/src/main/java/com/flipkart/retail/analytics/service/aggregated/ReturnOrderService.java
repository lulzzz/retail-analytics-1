package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.ROPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.ReturnOrderDao;
import com.flipkart.retail.analytics.persistence.entity.ReturnOrder;
import com.flipkart.retail.analytics.service.AggregatedService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler({
        @EntityHandler.Type(entityType = EntityType.RETURN_ORDER)
})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ReturnOrderService implements AggregatedService {
    private final ReturnOrderDao returnOrderDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = retailAnalyticsUtils.getTableName(reportsConfiguration.getReturnOrder());
        List<ReturnOrder> returnOrders = returnOrderDao.getReturnOrders(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (ReturnOrder returnOrder : returnOrders){
            ROPurchasingTrend roPurchasingTrend = new ROPurchasingTrend(returnOrder.getMonth(), returnOrder.getCurrency(),
                    returnOrder.getQuantity(), returnOrder.getAmount());
            purchasingTrends.add(roPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public void getAggregatedOperationalPerformance(List<String> vendorSites, List<String> warehouses) {

    }
}
