package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.dto.*;
import com.flipkart.retail.analytics.service.BaseAggregationService;
import com.flipkart.retail.analytics.service.PerformanceMetricsService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aggregate")
@Api(value = "/aggregate", description = "Aggregate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AggregateResource {
    private final BaseAggregationService baseAggregationService;
    private final PerformanceMetricsService performanceMetricsService;

    @Path("/purchasing-trends")
    @POST
    @ApiOperation(value = "Aggregated purchasing trends")
    @Timed
    @ExceptionMetered
    public Response getAggregatedPurchasingTrends(AggregatedPurchasingTrendRequest aggregatedPurchasingTrendRequest) {
        AggregatedPurchasingTrendResponse aggregatedPurchasingTrendResponse = baseAggregationService
                .getAggregatedPurchasingTrends(aggregatedPurchasingTrendRequest);
        return Response.ok(aggregatedPurchasingTrendResponse).build();
    }

    @Path("/operational-performance")
    @POST
    @ApiOperation(value = "Aggregated operational performance")
    @Timed
    @ExceptionMetered
    public Response getAggregatedOperationalPerformance(OperationalPerformanceRequest operationalPerformanceRequest) {
        OperationalPerformanceResponse operationalPerformanceResponse = performanceMetricsService
                .getAggregatedOperationalPerformance(operationalPerformanceRequest);
        return Response.ok(operationalPerformanceResponse).build();
    }

    @Path("/details")
    @POST
    @ApiOperation(value = "Aggregated details")
    @Timed
    @ExceptionMetered
    public Response getAggregatedPO(@Valid AggregatedDetailedRequest aggregatedDetailedRequest) {
        AggregatedDetailedResponse aggregatedDetailedResponse = baseAggregationService.getAggregatedDetails
                (aggregatedDetailedRequest);
        return Response.ok(aggregatedDetailedResponse).build();
    }
}
