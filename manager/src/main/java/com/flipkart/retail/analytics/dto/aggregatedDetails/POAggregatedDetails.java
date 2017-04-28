package com.flipkart.retail.analytics.dto.aggregatedDetails;

import com.flipkart.retail.analytics.dto.AggregatedDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class POAggregatedDetails extends AggregatedDetails {
    private Long openPo;
    private List<PODetails> details;

    @Data
    public class PODetails implements Serializable  {
        private String status;
        private String currency;
        private int uniqueProducts;
        private Long receivedUnits;
        private Long pendingUnits;
        private Long cancelledUnits;
        private Double receivedAmount;
        private Double pendingAmount;
        private Double cancelledAmount;
    }
}
