package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.PaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;

import javax.inject.Provider;
import javax.persistence.EntityManager;

public class PaymentsManagerImpl extends SimpleJpaGenericRepository<Payment, Long> implements PaymentsManager {

    @Inject
    public PaymentsManagerImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }
}
