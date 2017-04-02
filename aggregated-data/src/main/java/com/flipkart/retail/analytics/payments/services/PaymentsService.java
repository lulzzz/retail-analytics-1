package com.flipkart.retail.analytics.payments.services;


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
                    .paymentDate(payment.getPaidDate())
                    .build();

        }
        return vendorSitePaymentsDetails;
    }
}
