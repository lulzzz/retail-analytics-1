package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceMetrics {
    private String month;
    private Double fillRate;
    private Double leadTime;
    private Double qcReject;
    private Double qcRejectWithoutExcess;
    private Double roApprovalTat;
    private Double roApprovedEager;
    private Double roReject;
}
