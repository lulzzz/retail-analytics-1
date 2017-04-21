package com.flipkart.retail.analytics.persistence.entity;

import lombok.Data;

@Data
public class PurchaseOrderDetails {
    private String status;
    private String currency;
    private Long totalReceivedUnits;
    private Long totalPendingUnits;
    private Long totalCancelledUnits;
    private Double totalReceivedAmount;
    private Double totalPendingAmount;
    private Double totalCancelledAmount;
}
