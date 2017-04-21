package com.flipkart.retail.analytics.dto.aggregatedDetails;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import lombok.Data;

@Data
public class POAggregatedDetails extends AggregatedDetails {
    private String status;
    private String currency;
    private int uniqueProducts;
    private Long totalReceivedUnits;
    private Long totalPendingUnits;
    private Long totalCancelledUnits;
    private Double totalReceivedAmount;
    private Double totalPendingAmount;
    private Double totalCancelledAmount;
}
