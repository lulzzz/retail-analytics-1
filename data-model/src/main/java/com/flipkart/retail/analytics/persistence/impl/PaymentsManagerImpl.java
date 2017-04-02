package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.PaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class PaymentsManagerImpl extends SimpleJpaGenericRepository<Payment, Long> implements PaymentsManager {

    @Inject
    public PaymentsManagerImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<Payment> getLastPaymentByVs(List<String> vendorSiteIds) {

        Query query = getEntityManager().createNamedQuery("findLastPaymentByVsIds")
                .setParameter("vendorSiteIds", vendorSiteIds)
                .setMaxResults(1);
       return Optional.of((Payment) query.getResultList().get(0));

    }
}
