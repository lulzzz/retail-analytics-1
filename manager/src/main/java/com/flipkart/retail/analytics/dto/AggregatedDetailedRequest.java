package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class AggregatedDetailedRequest {
    private List<EntityType> entities;
    private List<String> vendorSites;

    @Valid @NotNull
    @Pattern(regexp="[0-9]{4}-[0-9]{2}", message="Invalid From Date")
    private String fromMonth;

    @Valid @NotNull
    @Pattern(regexp="[0-9]{4}-[0-9]{2}", message="Invalid To Date")
    private String toMonth;
}
