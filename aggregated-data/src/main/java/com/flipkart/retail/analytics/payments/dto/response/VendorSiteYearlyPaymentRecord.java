package com.flipkart.retail.analytics.payments.dto.response;

import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;


@Data
public class VendorSiteYearlyPaymentRecord {


    private List<String> vendorSiteId;

    private String startYear;

    private String endYear;

    private List<PaymentCurrencyMap> payments;

}
