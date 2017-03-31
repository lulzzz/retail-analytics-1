package com.flipkart.retail.analytics.payments.services;

import com.flipkart.retail.analytics.payments.dto.response.VendorSiteYearlyPaymentRecord;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.flipkart.retail.analytics.persistence.impl.AggregatedPaymentsManagerImpl;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PaymentAggregatorService {

    private AggregatedPaymentsManagerImpl aggregatedPaymentsManager;

    public VendorSiteYearlyPaymentRecord getVendorSitePaymentDetails(List<String> vendorSiteIds, String startYear, String endYear) {
        BigDecimal totalPayment = null;
        Optional<List<VendorSiteYearlyPayment>> aggregatedPayments = getPaymentByVendorSites(vendorSiteIds,startYear, endYear);
        if(aggregatedPayments.isPresent()) {
           totalPayment =  aggregatedPayments.get().stream()
                                    .map(VendorSiteYearlyPayment::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return VendorSiteYearlyPaymentRecord
                .builder()
                .amount(totalPayment)
                .vendorSiteId(vendorSiteIds)
                .startYear(startYear)
                .endYear(endYear)
                .build();
    }

    private Optional<List<VendorSiteYearlyPayment>> getPaymentByVendorSites(List<String> vendorSiteIds, String startYear, String endYear){
        if(vendorSiteIds.size() == 0){
            return Optional.empty();
        }
        return aggregatedPaymentsManager.getPaymentByVendorSites(vendorSiteIds, startYear, endYear);
    }


}
