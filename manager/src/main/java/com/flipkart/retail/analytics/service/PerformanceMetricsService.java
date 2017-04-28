package com.flipkart.retail.analytics.service;


import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.OperationalPerformanceRequest;
import com.flipkart.retail.analytics.dto.OperationalPerformanceResponse;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.persistence.PerformanceMetricsManager;
import com.flipkart.retail.analytics.persistence.entity.PerformanceMetrics;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.cache.annotation.CacheResult;
import java.util.*;

@RequiredArgsConstructor(onConstructor = @__(@javax.inject.Inject))
public class PerformanceMetricsService {
    private final PerformanceMetricsManager performanceMetricsManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @CacheResult(cacheName = "operational_performance_cache")
    public OperationalPerformanceResponse getAggregatedOperationalPerformance(OperationalPerformanceRequest operationalPerformanceRequest){
        OperationalPerformanceResponse operationalPerformanceResponse = new OperationalPerformanceResponse();
        Map<MetricType, List<OperationalPerformance>> operationalPerformance = new HashMap<>();
        List<PerformanceMetrics> performanceMetricsList = performanceMetricsManager.getPerformanceMetrics(getMetricsTable
                (), operationalPerformanceRequest.getVendorSites(), operationalPerformanceRequest.getWarehouses());
        for (MetricType metricType : operationalPerformanceRequest.getMetrics()) {
            List<OperationalPerformance> metricOperationalPerformances = getPerformanceMetrics(metricType,
                    performanceMetricsList);
            operationalPerformance.put(metricType, metricOperationalPerformances);

        }
        operationalPerformanceResponse.setOperationalPerformance(operationalPerformance);
        return operationalPerformanceResponse;
    }

    private List<OperationalPerformance> getPerformanceMetrics(MetricType metricType, List<PerformanceMetrics> performanceMetricsList){
        List<OperationalPerformance> operationalPerformanceList = new ArrayList<>();
        switch (metricType){
            case FILL_RATE:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getFillRate())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getFillRate()));
                    }
                });
                break;
            case LEAD_TIME:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getLeadTime())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getLeadTime()));
                    }
                });
                break;
            case QC_REJECT_WITHOUT_EXCESS:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getQcRejectWithoutExcess())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getQcRejectWithoutExcess()));
                    }
                });
                break;
            case QC_REJECT:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getQcReject())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getQcReject()));
                    }
                });
                break;
            case RO_APPROVAL_TAT:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getRoApprovalTat())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getRoApprovalTat()));
                    }
                });
                break;
            case RO_APPROVED_EAGER:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getRoApprovedEager())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getRoApprovedEager()));
                    }
                });
                break;
            case RO_REJECTED:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getRoReject())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getRoReject()));
                    }
                });
                break;
            case RO_WITHOUT_ACTION:
                performanceMetricsList.stream().forEach(performanceMetrics -> {
                    if (Objects.nonNull(performanceMetrics.getRoUnActioned())) {
                        operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                                performanceMetrics.getRoUnActioned()));
                    }
                });
                break;
        }
        return operationalPerformanceList;
    }

    private String getMetricsTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getMetrics());
    }

}
