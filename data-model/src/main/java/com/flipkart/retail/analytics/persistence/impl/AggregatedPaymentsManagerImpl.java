package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.AggregatedPaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.AggregatedPayment;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class AggregatedPaymentsManagerImpl extends SimpleJpaGenericRepository<AggregatedPayment, Long>
        implements AggregatedPaymentsManager{

    @Inject
    public AggregatedPaymentsManagerImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

//    @Override
//    public Optional<AggregatedPayment> getPaymentByVendorSites(List<String> vendorSiteIds, String startYear, String endYear) {
//
//       Query query = getEntityManager().createNamedQuery("findPaymentsByVendorSiteIds")
//               .setParameter("vendorSiteIds", vendorSiteIds)
//               .setParameter("startDate",startYear)
//                .setParameter("endDate", endYear);
//
//          return Optional.of(query.getResultList());
//    }
}
