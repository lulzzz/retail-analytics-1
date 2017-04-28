package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.exception.AuthServiceException;
import com.flipkart.retail.analytics.payments.dto.response.VendorSitePaymentsDetails;
import com.flipkart.retail.analytics.payments.services.PaymentsService;
import com.flipkart.retail.analytics.persistence.dto.request.PaymentSearchRequest;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/payment")
@Api(value = "/payment", description = "aggregation pipeline")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    private PaymentsService paymentsService;

    @GET
    @Path("/last-payment")
    @ApiOperation(value = "last-payment")
    @Timed
    public Response getLastPaymentByVs(@NotNull @QueryParam("vendor_site_ids") String vendorSiteId) {
        List<String> vendorSiteIds = Arrays.asList(vendorSiteId.split(","));
        VendorSitePaymentsDetails vendorSitePaymentsDetails = paymentsService.getLastPaymentByVs(vendorSiteIds);
        if (vendorSitePaymentsDetails == null) {
            return Response.status(404).build();
        }
        return Response.ok(vendorSitePaymentsDetails).build();

    }

    @GET
    @Path("/{payment_id}")
    @ApiOperation(value = "Get payment ids for a particular invoice id")
    @Timed
    public Response getPaymentsFromId(@NotNull @PathParam("payment_id") String paymentId){
        try {
            return Response.ok(paymentsService.getPaymentsDetails(paymentId)).build();
        }catch (AuthServiceException e){
            return Response.status(e.getHttpStatusCode()).entity(e.toJson()).build();
        }
    }

    @POST
    @Path("/search")
    @ApiOperation(value = "Get payment ids for a particular invoice id")
    @Timed
    public Response getPaymentDetailsFromVendorSites(@Valid PaymentSearchRequest paymentSearchRequest)
    {
        return Response.ok(paymentsService.getPaymentFromVendorSites(paymentSearchRequest)).build();
    }

}
