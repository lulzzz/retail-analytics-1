package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Data
@Path("/aggregator")
@Api(value = "/aggregator", description = "aggregation pipeline")
@Produces(MediaType.APPLICATION_JSON)
public class AggregatorResource {

    @GET
    @Path("/testApi")
    @ApiOperation(value = "TestApi")
    @Timed
    public Response getAveragePurchasePrice() {
        return Response.ok("hello").build();
    }


}
