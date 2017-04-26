package com.flipkart.retail.analytics.resources;


import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.common.YearClassifier;
import com.flipkart.retail.analytics.payments.services.PaymentAggregatorService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Data
@Path("/aggregated-payments")
@Api(value = "/aggregated-payments", description = "vendorSite aggregated details")
@Produces(MediaType.APPLICATION_JSON)
public class AggregatedPaymentResource {


   @Inject
   private PaymentAggregatorService paymentAggregatorService;

    @GET
    @Path("/last-year")
    @ApiOperation(value = "Payment aggregated data for vendor")
    @Timed
    @ExceptionMetered
    public Response getVendorSitePaymentDetails(@NotNull @QueryParam("vendor_sites") String vendorSiteId,
                                                @NotNull @QueryParam("year") YearClassifier year) {
        List<String> vendorSiteIds = Arrays.asList(vendorSiteId.split(","));
        return Response.ok(paymentAggregatorService.getLastYearPaymentDetails(vendorSiteIds, year)).build();
    }
}
