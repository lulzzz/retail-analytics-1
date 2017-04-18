package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.IRN;

import java.util.List;

public interface IRNDao {
    List<IRN> getIRNs(String tableName, List<String> vendorSites, List<String> warehouses);
}
