package com.flipkart.retail.analytics.payments.services;

import com.flipkart.retail.analytics.common.YearClassifier;
import com.flipkart.retail.analytics.payments.dto.response.VendorSiteYearlyPaymentRecord;
import com.flipkart.retail.analytics.persistence.AggregatedPaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.flipkart.retail.analytics.persistence.impl.AggregatedPaymentsManagerImpl;
import com.flipkart.retail.analytics.persistence.utility.Currencies;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PaymentAggregatorService {

    private final AggregatedPaymentsManager aggregatedPaymentsManager;

    public VendorSiteYearlyPaymentRecord getLastYearPaymentDetails(List<String> vendorSiteIds, YearClassifier yearClassifier) {
        BigDecimal totalPayment = null;
        String startYear = String.valueOf(DateTime.now().getYear()-1);
        String endYear = String.valueOf(DateTime.now().getYear()) ;

        if(YearClassifier.FY.equals(yearClassifier)){
            startYear = startYear.concat("04");
            endYear = endYear.concat("04");
        }
        else{
            startYear = startYear.concat("01");
            endYear = endYear.concat("01");
        }
        Optional<List<VendorSiteYearlyPayment>> aggregatedPayments = getYearlyPaymentByVs(vendorSiteIds, startYear, endYear);
        if(aggregatedPayments.isPresent()) {
           totalPayment =  aggregatedPayments.get().stream()
                                    .map(VendorSiteYearlyPayment::getAmount)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return VendorSiteYearlyPaymentRecord
                .builder()
                .amount(totalPayment)
                .vendorSiteId(vendorSiteIds)
                .currency(Currencies.valueOf(aggregatedPayments.get().get(0).getCurrency()))
                .startYear(startYear)
                .endYear(endYear)
                .build();
    }

    @Transactional
    private Optional<List<VendorSiteYearlyPayment>> getYearlyPaymentByVs(List<String> vendorSiteIds, String startYear, String endYear){

        if(vendorSiteIds.size() == 0){
            return Optional.empty();
        }
        return aggregatedPaymentsManager.getPaymentByVendorSites(vendorSiteIds, startYear, endYear);
    }


}
