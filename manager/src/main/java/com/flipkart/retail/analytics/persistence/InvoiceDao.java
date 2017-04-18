package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.Invoice;

import java.util.List;

public interface InvoiceDao {
    List<Invoice> getInvoice(String tableName, List<String> vendorSites, List<String> warehouses);
}
