package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.Payment;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

public interface PaymentsManager extends JpaGenericRepository<Payment, Long> {

}
