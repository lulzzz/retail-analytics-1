package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.annotations.MetricHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.QCPurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.persistence.QCDao;
import com.flipkart.retail.analytics.persistence.entity.QC;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.QUALITY_CHECK)
@MetricHandler({
        @MetricHandler.Type(metricType = MetricType.QC_REJECT),
        @MetricHandler.Type(metricType = MetricType.QC_REJECT_WITH_EXCESS)
})
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class QCService implements AggregationService {
    private final QCDao qcDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = retailAnalyticsUtils.getTableName(reportsConfiguration.getQc());
        List<QC> qcs = qcDao.getQC(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (QC qc : qcs){
            QCPurchasingTrend qcPurchasingTrend = new QCPurchasingTrend(qc.getMonth(), qc.getCurrency(), qc
                    .getRejectQty(), qc.getRejectAmount(), qc.getExcessQty(), qc.getExcessAmount());
            purchasingTrends.add(qcPurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public List<OperationalPerformance> getAggregatedOperationalPerformance(MetricType metricType, List<String> vendorSites, List<String> warehouses) {
        return null;
    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        return null;
    }
}
