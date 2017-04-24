package com.flipkart.retail.analytics.dto;

import com.flipkart.retail.analytics.enums.EntityType;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class AggregatedDetailedRequest {
    private List<EntityType> entities;
    private List<String> vendorSites;

    @Pattern(regexp="[0-9]{4}-[0-9]{2}", message="Invalid From Date")
    private String fromMonth;

    @Pattern(regexp="[0-9]{4}-[0-9]{2}", message="Invalid To Date")
    private String toMonth;
}
