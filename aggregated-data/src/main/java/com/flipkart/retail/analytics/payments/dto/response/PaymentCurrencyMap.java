package com.flipkart.retail.analytics.payments.dto.response;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentCurrencyMap {

    private String currency;

    private BigDecimal amount;
}
