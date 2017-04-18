package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QC {
    private String month;
    private Integer rejectQty;
    private Double rejectAmount;
    private Integer excessQty;
    private Double excessAmount;
    private String currency;
}
