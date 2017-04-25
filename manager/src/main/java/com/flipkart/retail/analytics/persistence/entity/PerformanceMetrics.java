package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerformanceMetrics {
    private String month;
    private String fillRate;
    private String leadTime;
    private String qcReject;
    private String qcRejectWithoutExcess;
    private String roApprovalTat;
    private String roApprovedEager;
    private String roReject;
    private String roUnActioned;
}
