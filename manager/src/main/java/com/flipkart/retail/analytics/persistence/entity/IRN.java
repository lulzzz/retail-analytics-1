package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IRN {
    private String month;
    private Integer quantity;
    private Double amount;
}
