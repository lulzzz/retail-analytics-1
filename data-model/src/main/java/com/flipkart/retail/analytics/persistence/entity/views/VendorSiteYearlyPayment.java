package com.flipkart.retail.analytics.persistence.entity.views;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VendorSiteYearlyPayment {

    @Id
    String vendorSiteId;

    BigDecimal amount;

    String currency;

}
