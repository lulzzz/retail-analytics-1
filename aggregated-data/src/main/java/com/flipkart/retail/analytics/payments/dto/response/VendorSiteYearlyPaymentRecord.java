package com.flipkart.retail.analytics.payments.dto.response;

import com.flipkart.retail.analytics.persistence.utility.Currencies;
import lombok.*;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorSiteYearlyPaymentRecord {


    private List<String> vendorSiteId;

    private String startYear;

    private String endYear;

    private Currencies currency;

    private BigDecimal amount;
}
