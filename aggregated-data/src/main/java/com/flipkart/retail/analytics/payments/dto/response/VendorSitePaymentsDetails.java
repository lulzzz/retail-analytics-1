package com.flipkart.retail.analytics.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flipkart.retail.analytics.persistence.utility.Currencies;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class VendorSitePaymentsDetails {

    private String vendorSiteId;

    private BigDecimal amount;

    private Currencies currency;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime paymentDate;
}
