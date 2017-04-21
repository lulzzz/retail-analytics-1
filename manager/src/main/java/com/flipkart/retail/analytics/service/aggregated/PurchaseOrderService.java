package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.annotations.MetricHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.POAggregatedDetails;
import com.flipkart.retail.analytics.dto.purchasingTrend.POPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.persistence.PurchaseOrderDao;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.PURCHASE_ORDER)
@MetricHandler({
        @MetricHandler.Type(metricType = MetricType.FILL_RATE),
        @MetricHandler.Type(metricType = MetricType.LEAD_TIME)
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
    public List<OperationalPerformance> getAggregatedOperationalPerformance(MetricType metricType, List<String> vendorSites,
                                                                            List<String> warehouses) {
        List<OperationalPerformance> operationalPerformances = new ArrayList<>();
        if (metricType.equals(MetricType.LEAD_TIME)) {
            List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getLeadTime(getPoTable(), vendorSites, warehouses);
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                OperationalPerformance operationalPerformance = new OperationalPerformance(purchaseOrder.getMonth(),
                        purchaseOrder.getAmount());
                operationalPerformances.add(operationalPerformance);
            }

        } else if (metricType.equals(MetricType.FILL_RATE)) {
            List<PurchaseOrder> purchaseOrders = purchaseOrderDao.getFillRate(getPoTable(), vendorSites, warehouses);
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                OperationalPerformance operationalPerformance = new OperationalPerformance(purchaseOrder.getMonth(),
                        purchaseOrder.getAmount());
                operationalPerformances.add(operationalPerformance);
            }
        }
        return operationalPerformances;
    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        List<POAggregatedDetails> poAggregatedDetailsList = purchaseOrderDao.getPurchaseOrderDetails(getPoTable(), vendorSites);
        List<AggregatedDetails> aggregatedDetailsList = new ArrayList<>();
        for(POAggregatedDetails poAggregatedDetails : poAggregatedDetailsList){
            aggregatedDetailsList.add(poAggregatedDetails);
        }
        return aggregatedDetailsList;
    }

    private String getPoTable(){
        return retailAnalyticsUtils.getTableName(reportsConfiguration.getPurchaseOrder());
    }
}
