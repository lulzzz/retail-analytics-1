package com.flipkart.retail.analytics.dto.aggregatedDetails;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import lombok.Data;

@Data
public class ROAggregatedDetails extends AggregatedDetails {
    private String status;
    private String currency;
    private int uniqueProducts;
    private Long totalUnits;
    private Long totalProcessedUnits;
    private Double totalAmount;
    private Double totalProcessedAmount;
}
