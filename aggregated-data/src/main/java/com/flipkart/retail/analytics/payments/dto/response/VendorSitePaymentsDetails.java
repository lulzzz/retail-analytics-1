package com.flipkart.retail.analytics.payments.dto.response;

import com.flipkart.retail.analytics.persistence.utility.Currencies;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class VendorSitePaymentsDetails {

    private String vendorSiteId;

    private BigDecimal amount;

    private Currencies currency;

    private String paymentDate;
}
