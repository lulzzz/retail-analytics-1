package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.IRNPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.persistence.IRNDao;
import com.flipkart.retail.analytics.persistence.entity.IRN;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.IRN)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class IRNService implements AggregationService {
    private final IRNDao irnDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = retailAnalyticsUtils.getTableName(reportsConfiguration.getIrn());
        List<IRN> irns = irnDao.getIRNs(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (IRN irn : irns){
            IRNPurchasingTrend irnPurchasingTrend = new IRNPurchasingTrend(irn.getMonth(), irn.getQuantity(), irn.getAmount());
            purchasingTrends.add(irnPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public List<OperationalPerformance> getAggregatedOperationalPerformance(MetricType metricType, List<String> vendorSites, List<String> warehouses) {
        return null;
    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites) {
        return null;
    }
}
