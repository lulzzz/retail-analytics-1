package com.flipkart.retail.analytics.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by kushagra.gupta on 11/04/17.
 */
@Getter
@Setter
@JsonSnakeCase
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentListResponse {

    List<PaymentResponse> payments;
}
