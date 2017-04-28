package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.PaymentItem;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

import java.util.List;

public interface PaymentItemsPersistenceManager extends JpaGenericRepository<PaymentItem, Long> {
    List<PaymentItem> getAllPaymentIds(String invoiceId);
}
