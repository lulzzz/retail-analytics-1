package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.payments.dto.response.VendorSitePaymentsDetails;
import com.flipkart.retail.analytics.payments.services.PaymentsService;
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
@Path("/payment")
@Api(value = "/payment", description = "aggregation pipeline")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    private PaymentsService paymentsService;

    @GET
    @Path("/lastPayment")
    @ApiOperation(value = "lastPayment")
    @Timed
    public Response getLastPaymentByVs(@NotNull @QueryParam("vendorSiteIds") String vendorSiteId) {
        List<String> vendorSiteIds = Arrays.asList(vendorSiteId.split(","));
        return Response.ok(paymentsService.getLastPaymentByVs(vendorSiteIds)).build();
    }


}
