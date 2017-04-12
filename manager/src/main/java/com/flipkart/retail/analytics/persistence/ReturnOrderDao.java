package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.ReturnOrder;

import java.util.List;

public interface ReturnOrderDao {
    List<ReturnOrder> getReturnOrders(String tableName, List<String> vendorSites, List<String> warehouses);
}
