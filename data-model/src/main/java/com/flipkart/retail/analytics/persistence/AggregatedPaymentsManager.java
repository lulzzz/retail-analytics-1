package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.AggregatedPayment;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

import java.util.List;
import java.util.Optional;

public interface AggregatedPaymentsManager  extends JpaGenericRepository<AggregatedPayment, Long> {

    Optional<List<VendorSiteYearlyPayment>> getPaymentByVendorSites(List<String> vendorSites, String start, String end);
}
