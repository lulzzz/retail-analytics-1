package com.flipkart.retail.analytics.config;

import lombok.Data;

@Data
public class ReportsConfiguration {
    private String purchaseOrder;
    private String returnOrder;
    private String irn;
    private String invoice;
    private String qc;
}
