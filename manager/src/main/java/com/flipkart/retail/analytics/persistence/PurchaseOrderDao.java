package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.aggregatedDetails.POAggregatedDetails;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {
    List<PurchaseOrder> getPurchaseOrders(String tableName, List<String> vendorSites, List<String> warehouses);

    List<PurchaseOrder> getLeadTime(String tableName, List<String> vendorSites, List<String> warehouses);

    List<PurchaseOrder> getFillRate(String tableName, List<String> vendorSites, List<String> warehouses);

    List<POAggregatedDetails> getPurchaseOrderDetails(String tableName, List<String> vendorSites);
}
