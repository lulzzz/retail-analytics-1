package com.flipkart.retail.analytics.dto.aggregatedDetails;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ROAggregatedDetails extends AggregatedDetails {
    private Long pendingRo;
    private Long approvedRo;
    private Long rejectedRo;
    private List<RODetails> details;

    @Data
    public class RODetails{
        private String status;
        private String currency;
        private int uniqueProducts;
        private Long units;
        private Long processedUnits;
        private Double amount;
        private Double processedAmount;
    }
}
