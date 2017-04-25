package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ROAggregatedCount {
    private Long pendingRo;
    private Long approvedRo;
    private Long rejectedRo;
}
