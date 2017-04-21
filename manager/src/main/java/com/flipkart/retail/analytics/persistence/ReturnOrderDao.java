package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.aggregatedDetails.ROAggregatedDetails;
import com.flipkart.retail.analytics.persistence.entity.ReturnOrder;

import java.util.List;

public interface ReturnOrderDao {
    List<ReturnOrder> getReturnOrders(String tableName, List<String> vendorSites, List<String> warehouses);

    List<ReturnOrder> getROApprovalTAT(String tableName, List<String> vendorSites, List<String> warehouses);

    List<ReturnOrder> getROApprovedEager(String tableName, List<String> vendorSites, List<String> warehouses);

    List<ReturnOrder> getRORejected(String tableName, List<String> vendorSites, List<String> warehouses);

    List<ReturnOrder> getROWithoutAction(String tableName, List<String> vendorSites, List<String> warehouses);

    List<ROAggregatedDetails> getReturnOrderDetails(String tableName, List<String> vendorSites);
}
