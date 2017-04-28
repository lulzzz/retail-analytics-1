package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.PaymentsManager;
import com.flipkart.retail.analytics.persistence.dto.request.PaymentSearchRequest;
import com.flipkart.retail.analytics.persistence.entity.Payment;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.Page;
import fk.sp.common.extensions.jpa.PageRequest;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
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
       return query.getResultList().size() > 0 ? Optional.of((Payment) query.getResultList().get(0)): Optional.empty();

    }

    @Override
    public  Optional<Payment>getPayments(String paymentId)
    {
        log.debug("Fetching payments details {}",paymentId);
        Map<String, Object> params = new HashMap<>();
        params.put("refNumber", paymentId);
        Optional<Payment>
                payment=
                findOneByNamedQuery("Payment.findByRefNumber", params);

        return payment;
    }

    @Override
    public List<Payment> getPaymentByVendorSites(PaymentSearchRequest paymentSearchRequest)
    {
        log.debug("Fetching payment details for PaymentSearchRequest {}",paymentSearchRequest);
        PageRequest
                pageRequest =
                PageRequest.builder().pageNumber(paymentSearchRequest.getPageNumber()-1).
                        pageSize(paymentSearchRequest.getPageSize()).build();
        LocalDateTime fromDate = paymentSearchRequest.getFromDate();
        LocalDateTime toDate = paymentSearchRequest.getToDate();

        if(paymentSearchRequest.getFromDate() == null){
            Date today = new Date(0);
            fromDate = LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
        }
        if(paymentSearchRequest.getToDate() == null){
            toDate = LocalDateTime.now();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("vendorSiteIds", paymentSearchRequest.getVendorSites());
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);


        Page<Payment>
                paymentsPage =
                findAllByNamedQuery("Payment.findByVendorSites", params,
                        pageRequest);
        return paymentsPage.getContent();
    }

}
