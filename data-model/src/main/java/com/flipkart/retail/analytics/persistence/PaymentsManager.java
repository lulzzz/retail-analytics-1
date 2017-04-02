package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.Payment;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentsManager extends JpaGenericRepository<Payment, Long> {

    public Optional<Payment> getLastPaymentByVs(List<String> vendorSiteIds);
}
