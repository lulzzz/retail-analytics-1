package com.flipkart.retail.analytics.resources;


import com.codahale.metrics.annotation.Timed;
import com.flipkart.retail.analytics.payments.dto.response.VendorSiteYearlyPaymentRecord;
import com.flipkart.retail.analytics.payments.services.PaymentAggregatorService;
import com.google.inject.Inject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Data
@Path("/payments")
@Api(value = "/payments", description = "vendorSite aggregated details")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {


   @Inject
   private PaymentAggregatorService paymentAggregatorService;

    @GET
    @Path("/vendorSites")
    @ApiOperation(value = "Payment aggregated data for vendor")
    @Timed
    public VendorSiteYearlyPaymentRecord getVendorSitePaymentDetails(@QueryParam("vendorSites") List<String> vendorSitesIds,
                                                @QueryParam("startYear") String startYear,
                                                @QueryParam("endYear") String endYear) {
        return paymentAggregatorService.getVendorSitePaymentDetails(vendorSitesIds, startYear, endYear);
    }

}
