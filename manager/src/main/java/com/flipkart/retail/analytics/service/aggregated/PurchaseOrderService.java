package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.POPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.PurchaseOrderDao;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler({
        @EntityHandler.Type(entityType = EntityType.PURCHASE_ORDER)
})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PurchaseOrderService implements AggregationService {
    private final PurchaseOrderDao purchaseOrderDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getPurchaseOrders(getPoTable(), vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders){
            POPurchasingTrend poPurchasingTrend = new POPurchasingTrend(purchaseOrder.getMonth(), purchaseOrder
                    .getCurrency(), purchaseOrder.getQuantity(), purchaseOrder.getAmount());
            purchasingTrends.add(poPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public void getAggregatedOperationalPerformance(List<String> vendorSites, List<String> warehouses) {

    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites) {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getPurchaseOrderDetails(getPoTable(), vendorSites);
        List<AggregatedDetails> aggregatedDetailsList = new ArrayList<>();
        for(PurchaseOrder purchaseOrder : purchaseOrderList){
            AggregatedDetails aggregatedDetails = new AggregatedDetails(purchaseOrder.getStatus(), purchaseOrder
                    .getCurrency(), purchaseOrder.getQuantity(), purchaseOrder.getAmount());
            aggregatedDetailsList.add(aggregatedDetails);
        }
        return aggregatedDetailsList;
    }

    private String getPoTable(){
        return retailAnalyticsUtils.getTableName(reportsConfiguration.getPurchaseOrder());
    }
}
