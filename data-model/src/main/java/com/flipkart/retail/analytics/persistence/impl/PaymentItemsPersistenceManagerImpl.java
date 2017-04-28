package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.PaymentItemsPersistenceManager;
import com.flipkart.retail.analytics.persistence.entity.PaymentItem;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
public class PaymentItemsPersistenceManagerImpl extends SimpleJpaGenericRepository<PaymentItem,Long>
        implements PaymentItemsPersistenceManager {

    @Inject
    public PaymentItemsPersistenceManagerImpl(
            Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public List<PaymentItem> getAllPaymentIds(String invoiceId)
    {
        log.debug("Fetching all the paymentsItems {}",invoiceId);
        Query query=getEntityManager().createNamedQuery("PaymentItem.findByItemRefNumber");
        query.setParameter("itemRefNumber",invoiceId);
        return query.getResultList();
    }

}
