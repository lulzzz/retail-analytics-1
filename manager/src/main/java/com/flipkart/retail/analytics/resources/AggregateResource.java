package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.dto.AggregatedPurchasingTrendResponse;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.service.BaseAggregationService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/aggregate")
@Api(value = "/aggregate", description = "Aggregate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AggregateResource {
    private final BaseAggregationService baseAggregationService;

    @GET
    @ApiOperation(value = "Aggregate")
    @Timed
    public Response getAggregatedResponse(@QueryParam("entity") List<EntityType> entities, @QueryParam("vendor_sites")
                                          List<String> vendorSites, @QueryParam("warehouses") List<String> warehouses){
        AggregatedPurchasingTrendResponse aggregatedPurchasingTrendResponse = baseAggregationService
                .getAggregatedPurchasingTrends(entities, vendorSites, warehouses);
        return Response.ok(aggregatedPurchasingTrendResponse).build();
    }
}
