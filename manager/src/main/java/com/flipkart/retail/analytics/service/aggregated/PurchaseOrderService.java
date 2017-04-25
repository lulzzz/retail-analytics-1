package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.POAggregatedDetails;
import com.flipkart.retail.analytics.dto.purchasingTrend.POPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.PurchaseOrderManager;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;
import com.flipkart.retail.analytics.service.AggregationService;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.PURCHASE_ORDER)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PurchaseOrderService implements AggregationService {
    private final PurchaseOrderManager purchaseOrderManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderManager.getPurchaseOrders(getPoTable(), vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders){
            POPurchasingTrend poPurchasingTrend = new POPurchasingTrend(purchaseOrder.getMonth(), purchaseOrder
                    .getCurrency(), purchaseOrder.getQuantity(), purchaseOrder.getAmount());
            purchasingTrends.add(poPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        List<POAggregatedDetails.PODetails> poDetails = purchaseOrderManager.getPurchaseOrderDetails(getPoTable(),
                vendorSites, fromMonth, toMonth);
        Long pendingPO = purchaseOrderManager.getAggregatedPOCount(getMetricsTable(), vendorSites);
        return new POAggregatedDetails(pendingPO, poDetails);
    }

    private String getPoTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getPurchaseOrder());
    }

    private String getMetricsTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getMetrics());
    }
}
