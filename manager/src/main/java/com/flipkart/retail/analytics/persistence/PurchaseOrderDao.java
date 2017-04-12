package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderDao {
    List<PurchaseOrder> getPurchaseOrders(String tableName, List<String> vendorSites, List<String> warehouses);
}
