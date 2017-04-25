package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.IRNPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.IRNManager;
import com.flipkart.retail.analytics.persistence.entity.IRN;
import com.flipkart.retail.analytics.service.AggregationService;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.IRN)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IRNService implements AggregationService {
    private final IRNManager irnManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = tableNameSelector.getActiveTableName(reportsConfiguration.getIrn());
        List<IRN> irns = irnManager.getIRNs(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (IRN irn : irns){
            IRNPurchasingTrend irnPurchasingTrend = new IRNPurchasingTrend(irn.getMonth(), irn.getQuantity(), irn.getAmount());
            purchasingTrends.add(irnPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        return null;
    }
}
