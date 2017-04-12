package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.POPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.PurchaseOrderDao;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;
import com.flipkart.retail.analytics.service.AggregatedService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler({
        @EntityHandler.Type(entityType = EntityType.PURCHASE_ORDER)
})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PurchaseOrderService implements AggregatedService {
    private final PurchaseOrderDao purchaseOrderDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = retailAnalyticsUtils.getTableName(reportsConfiguration.getPurchaseOrder());
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getPurchaseOrders(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders){
            POPurchasingTrend poPurchasingTrend = new POPurchasingTrend(purchaseOrder.getMonth(), purchaseOrder
                    .getReceivedQuantity(), purchaseOrder.getAmount());
            purchasingTrends.add(poPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public void getAggregatedOperationalPerformance(List<String> vendorSites, List<String> warehouses) {

    }
}
