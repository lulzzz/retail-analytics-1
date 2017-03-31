package com.flipkart.retail.analytics.payments.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class TotalPayment {

    private BigDecimal totalAmount;

}
