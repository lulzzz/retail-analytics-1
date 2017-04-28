package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.ROAggregatedDetails;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.ReturnOrderManager;
import com.flipkart.retail.analytics.persistence.entity.ROAggregatedCount;
import com.flipkart.retail.analytics.service.AggregationService;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.List;

@EntityHandler(entityType = EntityType.RETURN_ORDER)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ReturnOrderService implements AggregationService {
    private final ReturnOrderManager returnOrderManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        return returnOrderManager.getReturnOrders(getROTable(), vendorSites, warehouses);
    }

    @Override
    public AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        List<ROAggregatedDetails.RODetails> roDetails = returnOrderManager.getReturnOrderDetails(getROTable(),
                vendorSites);
        ROAggregatedCount roAggregatedCount = returnOrderManager.getAggregatedROCount(getMetricsTable(), vendorSites);
        return new ROAggregatedDetails(roAggregatedCount.getPendingRo(), roAggregatedCount.getApprovedRo(),
                roAggregatedCount.getRejectedRo(), roDetails);
    }

    private String getROTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getReturnOrder());
    }

    private String getMetricsTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getMetrics());
    }
}
