package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.entity.views.VendorSiteYearlyPayment;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.management.Query;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton
public class NativeManagerImpl {

    Provider<EntityManager> entityManagerProvider;

    @Inject
    public NativeManagerImpl(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }


    public List<VendorSiteYearlyPayment> getPaymentByVendorSites(List<String> vendorSiteIds, String startYear, String endYear) {
        String query = "select ROUND(sum(amount),3) as amount, currency from aggregated_payments where vendor_site_id in (:vendorSiteIds) and (month between :startYear and :endYear) group by currency";
        return  entityManagerProvider.get()
                .createNativeQuery(query, VendorSiteYearlyPayment.class)
                .setParameter("vendorSiteIds",vendorSiteIds)
                .setParameter("startYear",startYear)
                .setParameter("endYear", endYear)
                .getResultList();
    }

}
