package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.AggregatedPaymentsManager;
import com.flipkart.retail.analytics.persistence.entity.AggregatedPayment;
import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.google.inject.Inject;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AggregatedPaymentsManagerImpl extends SimpleJpaGenericRepository<AggregatedPayment, Long>
        implements AggregatedPaymentsManager{

    @Inject
    public AggregatedPaymentsManagerImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<List<VendorSiteYearlyPayment>> getPaymentByVendorSites(List<String> vendorSiteIds, String startYear, String endYear) {

        if(startYear==null){
            startYear = String.valueOf(DateTime.now().getYear());
        }
        if(endYear==null){
            endYear = String.valueOf(Integer.valueOf(startYear)-1);
        }

        List<VendorSiteYearlyPayment> vendorSiteIdAggregation  = getEntityManager()
               .createNamedQuery("AggregatedPayment.findPaymentsByVendorSiteIds")
               .setParameter("vendorSiteIds", vendorSiteIds)
               .setParameter("startDate",startYear.concat("01"))
                .setParameter("endDate", endYear.concat("01"))
               .getResultList();
          return Optional.of(vendorSiteIdAggregation);
    }
}
