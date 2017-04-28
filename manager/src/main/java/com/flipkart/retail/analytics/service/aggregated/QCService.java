package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.QCManager;
import com.flipkart.retail.analytics.service.AggregationService;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.List;

@EntityHandler(entityType = EntityType.QUALITY_CHECK)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class QCService implements AggregationService {
    private final QCManager qcManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = tableNameSelector.getActiveTableName(reportsConfiguration.getQc());
        return qcManager.getQC(tableName, vendorSites, warehouses);
    }

    @Override
    public AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        return null;
    }
}
