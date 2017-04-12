package com.flipkart.retail.analytics.payments.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kushagra.gupta on 11/04/17.
 */
@Getter
@Setter
@JsonSnakeCase
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class PaymentSearchRequest {

    @NotNull
    @Size(min=1)
    List<String> vendorSites;

    LocalDateTime fromDate;

    LocalDateTime toDate;

    @NotNull
    @Min(1)
    Integer pageNumber = 1;

    @NotNull
    @Min(1)
    @Max(50)
    Integer pageSize = 10;
}
