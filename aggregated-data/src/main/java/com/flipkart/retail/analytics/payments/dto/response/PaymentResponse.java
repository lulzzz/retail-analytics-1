package com.flipkart.retail.analytics.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kushagra.gupta on 03/02/17.
 */

@Getter
@Setter
@JsonSnakeCase
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {

    private String paymentId;

    private String paymentMethod;

    private String currency;

    private BigDecimal amount;

    private String bankName;

    private String bankBranchName;

    private String VendorName;

    private String  vendorSiteId;

    private String bankAccountNumber;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime paidDate;

    private List<PaymentItemsResponse> paymentItems;
}

