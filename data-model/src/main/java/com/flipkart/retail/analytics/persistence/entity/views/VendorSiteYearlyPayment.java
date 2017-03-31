package com.flipkart.retail.analytics.persistence.entity.views;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VendorSiteYearlyPayment {

    private String vendorSiteId;

    private BigDecimal amount;

}
