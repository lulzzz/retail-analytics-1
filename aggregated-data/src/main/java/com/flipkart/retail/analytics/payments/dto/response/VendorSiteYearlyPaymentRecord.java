package com.flipkart.retail.analytics.payments.dto.response;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Builder
public class VendorSiteYearlyPaymentRecord {


    private List<String> vendorSiteId;

    private String startYear;

    private String endYear;

    private BigDecimal amount;
}
