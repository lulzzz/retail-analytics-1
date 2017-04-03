package com.flipkart.retail.analytics.payments.services;

import com.flipkart.retail.analytics.common.YearClassifier;
import com.flipkart.retail.analytics.payments.dto.response.VendorSiteYearlyPaymentRecord;
import com.flipkart.retail.analytics.persistence.AggregatedPaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.flipkart.retail.analytics.persistence.impl.NativeManagerImpl;
import com.flipkart.retail.analytics.persistence.utility.Currencies;
import com.google.inject.Inject;
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
    private final NativeManagerImpl nativeManager;

    @Transactional
    public VendorSiteYearlyPaymentRecord getLastYearPaymentDetails(List<String> vendorSiteIds, YearClassifier yearClassifier) {
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

        VendorSiteYearlyPaymentRecord vendorSiteYearlyPaymentRecord = new VendorSiteYearlyPaymentRecord();
        vendorSiteYearlyPaymentRecord.setVendorSiteId(vendorSiteIds);
        vendorSiteYearlyPaymentRecord.setStartYear(startYear);
        vendorSiteYearlyPaymentRecord.setEndYear(endYear);

        Optional<List<VendorSiteYearlyPayment>> aggregatedPayments = getYearlyPaymentByVs(vendorSiteIds, startYear, endYear);
        if(aggregatedPayments.isPresent()) {
            BigDecimal totalPayment =  aggregatedPayments.get().stream().map(VendorSiteYearlyPayment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            vendorSiteYearlyPaymentRecord.setAmount(totalPayment);
            vendorSiteYearlyPaymentRecord.setCurrency(Currencies.valueOf(aggregatedPayments.get().get(0).getCurrency()));
        }
        return vendorSiteYearlyPaymentRecord;
    }

    @Transactional
    private Optional<List<VendorSiteYearlyPayment>> getYearlyPaymentByVs(List<String> vendorSiteIds, String startYear, String endYear){

        if(vendorSiteIds.size() == 0){
            return Optional.empty();
        }
        List<VendorSiteYearlyPayment> vendorSiteYearlyPayments = nativeManager.getPaymentByVendorSites(vendorSiteIds, startYear, endYear);
        return (vendorSiteYearlyPayments.size()!=0) ? Optional.of(vendorSiteYearlyPayments):Optional.empty();
    }


}
