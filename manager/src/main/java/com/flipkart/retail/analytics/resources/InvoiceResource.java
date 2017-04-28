package com.flipkart.retail.analytics.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.exception.AuthServiceException;
import com.flipkart.retail.analytics.payments.services.InvoiceService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kushagra.gupta on 11/04/17.
 */
@Path("/invoice")
@Api(value = "/roles", description = "invoices payments related api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvoiceResource {

    private final InvoiceService invoiceService;

    @GET
    @Path("/{invoice_id}/payment")
    @ApiOperation(value = "Get payment ids for a particular invoice id")
    @Timed
    @ExceptionMetered
    public Response getPaymentsIds(@NotEmpty @PathParam("invoice_id") String invoiceId){
        try {
            return Response.ok(invoiceService.getPayments(invoiceId)).build();
        }catch (AuthServiceException e){
            return Response.status(e.getHttpStatusCode()).entity(e.toJson()).build();
        }
    }

    @GET
    @Path("/{invoice_id}/payment_details")
    @ApiOperation(value = "Get payment ids for a particular invoice id")
    @Timed
    @ExceptionMetered
    public Response getPaymentsDetails(@NotEmpty @PathParam("invoice_id") String invoiceId){
        try {
            return Response.ok(invoiceService.getPaymentsDetails(invoiceId)).build();
        }catch (AuthServiceException e){
            return Response.status(e.getHttpStatusCode()).entity(e.toJson()).build();
        }
    }
}

