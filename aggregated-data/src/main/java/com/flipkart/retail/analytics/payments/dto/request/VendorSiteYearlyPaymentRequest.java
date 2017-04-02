package com.flipkart.retail.analytics.payments.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@Getter
@Setter
@JsonSnakeCase
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorSiteYearlyPaymentRequest {

    String startYear;

    String endYear;

    @NotEmpty
    List<String> vendorSiteIds;

}
