package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.dto.AggregatedDetailedRequest;
import com.flipkart.retail.analytics.dto.AggregatedDetailedResponse;
import com.flipkart.retail.analytics.dto.AggregatedPurchasingTrendRequest;
import com.flipkart.retail.analytics.dto.AggregatedPurchasingTrendResponse;
import com.flipkart.retail.analytics.service.BaseAggregationService;
import com.flipkart.retail.analytics.service.aggregated.PurchaseOrderService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aggregate")
@Api(value = "/aggregate", description = "Aggregate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AggregateResource {
    private final BaseAggregationService baseAggregationService;
    private final PurchaseOrderService purchaseOrderService;

    @Path("/purchasing-trends")
    @POST
    @ApiOperation(value = "Aggregated purchasing trends")
    @Timed
    public Response getAggregatedPurchasingTrends(AggregatedPurchasingTrendRequest aggregatedPurchasingTrendRequest){
        AggregatedPurchasingTrendResponse aggregatedPurchasingTrendResponse = baseAggregationService.getAggregatedPurchasingTrends(
                aggregatedPurchasingTrendRequest.getEntities(), aggregatedPurchasingTrendRequest.getVendorSites(), aggregatedPurchasingTrendRequest.getWarehouses());
        return Response.ok(aggregatedPurchasingTrendResponse).build();
    }

    @Path("/purchase-order")
    @POST
    @ApiOperation(value = "Aggregated purchase order")
    @Timed
    public Response getAggregatedPO(AggregatedDetailedRequest aggregatedDetailedRequest){
        AggregatedDetailedResponse aggregatedDetailedResponse = baseAggregationService.getAggregatedDetails
                (aggregatedDetailedRequest);
        return Response.ok(aggregatedDetailedResponse).build();
    }
}
