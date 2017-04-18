package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.QC;

import java.util.List;

public interface QCDao {
    List<QC> getQC(String tableName, List<String> vendorSites, List<String> warehouses);
}
