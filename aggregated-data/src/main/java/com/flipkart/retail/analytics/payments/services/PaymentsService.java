package com.flipkart.retail.analytics.payments.services;


import com.flipkart.retail.analytics.payments.dto.Mapper.PaymentToDtoMapper;
import com.flipkart.retail.analytics.payments.dto.request.PaymentSearchRequest;
import com.flipkart.retail.analytics.payments.dto.response.PaymentListResponse;
import com.flipkart.retail.analytics.payments.dto.response.PaymentResponse;
import com.flipkart.retail.analytics.payments.dto.response.VendorSitePaymentsDetails;
import com.flipkart.retail.analytics.persistence.PaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.flipkart.retail.analytics.persistence.impl.PaymentsManagerImpl;
import com.flipkart.retail.analytics.persistence.utility.Currencies;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PaymentsService {

    private final PaymentsManager paymentsManager;


    @Transactional
    public VendorSitePaymentsDetails getLastPaymentByVs(List<String> vendorSiteIds) {
        VendorSitePaymentsDetails vendorSitePaymentsDetails = null;
       Optional<Payment> paymentOptional = paymentsManager.getLastPaymentByVs(vendorSiteIds);
        if(paymentOptional.isPresent()){
            Payment payment = paymentOptional.get();
            vendorSitePaymentsDetails = VendorSitePaymentsDetails.builder()
                    .vendorSiteId(payment.getVendorSiteId())
                    .amount(payment.getAmount())
                    .currency(Currencies.valueOf(payment.getCurrency()))
                    .paymentDate(payment.getPaiddate())
                    .build();

        }
        return vendorSitePaymentsDetails;
    }

    @Transactional
    public PaymentResponse getPaymentsDetails(String paymentId) //throws AuthServiceException
    {
        Optional<Payment> payments = paymentsManager.getPayments(paymentId);

        if(!payments.isPresent())
        {
           // throw new AuthServiceException(ErrorCodes.INVALID_PAYMENT_ID);
        }

        return PaymentToDtoMapper.getPaymentResponse(payments.get());
    }

   /* @Transactional
    public PaymentListResponse getPaymentFromVendorSites(PaymentSearchRequest paymentSearchRequest)
    {

        List<Payment> paymentsList = paymentsManager.getPaymentByVendorSites(paymentSearchRequest);
        PaymentListResponse paymentListResponse=new PaymentListResponse();
        paymentListResponse.setPayments(paymentsList.stream().map(PaymentToDtoMapper::getPaymentResponse).collect(Collectors.toList()));
        return paymentListResponse;
    }*/


}
