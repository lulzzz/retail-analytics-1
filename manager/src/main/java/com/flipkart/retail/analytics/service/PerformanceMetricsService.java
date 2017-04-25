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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@javax.inject.Inject))
public class PerformanceMetricsService {
    private final PerformanceMetricsManager performanceMetricsManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    public OperationalPerformanceResponse getAggregatedOperationalPerformance(OperationalPerformanceRequest operationalPerformanceRequest){
        OperationalPerformanceResponse operationalPerformanceResponse = new OperationalPerformanceResponse();
        List<Map<MetricType, List<OperationalPerformance>>> operationalPerformanceList = new ArrayList<>();
        List<PerformanceMetrics> performanceMetricsList = performanceMetricsManager.getPerformanceMetrics(getMetricsTable
                (), operationalPerformanceRequest.getVendorSites(), operationalPerformanceRequest.getWarehouses());
        for (MetricType metricType : operationalPerformanceRequest.getMetrics()) {
            List<OperationalPerformance> operationalPerformances = getPerformanceMetrics(metricType,
                    performanceMetricsList);
            operationalPerformanceList.add(new HashMap<MetricType, List<OperationalPerformance>>(){{
                put(metricType, operationalPerformances);
            }});

        }
        operationalPerformanceResponse.setOperationalPerformance(operationalPerformanceList);
        return operationalPerformanceResponse;
    }

    private List<OperationalPerformance> getPerformanceMetrics(MetricType metricType, List<PerformanceMetrics> performanceMetricsList){
        List<OperationalPerformance> operationalPerformanceList = new ArrayList<>();
        switch (metricType){
            case FILL_RATE:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getFillRate()));
                }
                break;
            case LEAD_TIME:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getLeadTime()));
                }
                break;
            case QC_REJECT_WITHOUT_EXCESS:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getQcRejectWithoutExcess()));
                }
                break;
            case QC_REJECT:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getQcReject()));
                }
                break;
            case RO_APPROVAL_TAT:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getRoApprovalTat()));
                }
                break;
            case RO_APPROVED_EAGER:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getRoApprovedEager()));
                }
                break;
            case RO_REJECTED:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getRoReject()));
                }
                break;
            case RO_WITHOUT_ACTION:
                for(PerformanceMetrics performanceMetrics : performanceMetricsList){
                    operationalPerformanceList.add(new OperationalPerformance(performanceMetrics.getMonth(),
                            performanceMetrics.getRoUnActioned()));
                }
                break;
        }
        return operationalPerformanceList;
    }

    private String getMetricsTable(){
        return tableNameSelector.getActiveTableName(reportsConfiguration.getMetrics());
    }

}
