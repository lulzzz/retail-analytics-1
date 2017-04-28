package com.flipkart.retail.analytics.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by kushagra.gupta on 04/02/17.
 */

@Getter
@Setter
@JsonSnakeCase
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentItemsResponse {

    private Float itemNetAmount;

    private String invoiceId;

    private String txnNumber;

    private Float invoiceAmount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime invoiceDate;

    private String invoiceDescription;

    private Float prepaymentAmount;

    private String paymentType;
}
