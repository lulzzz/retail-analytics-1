package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.InvoicePurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.persistence.InvoiceManager;
import com.flipkart.retail.analytics.persistence.entity.Invoice;
import com.flipkart.retail.analytics.service.AggregationService;
import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.INVOICE)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvoiceService implements AggregationService {
    private final InvoiceManager invoiceManager;
    private final ReportsConfiguration reportsConfiguration;
    private final TableNameSelector tableNameSelector;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = tableNameSelector.getActiveTableName(reportsConfiguration.getInvoice());
        List<Invoice> invoices = invoiceManager.getInvoice(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (Invoice invoice : invoices){
            InvoicePurchasingTrend invoicePurchasingTrend = new InvoicePurchasingTrend(invoice.getMonth(), invoice
                    .getCurrency(), invoice.getQuantity(), invoice.getAmount());
            purchasingTrends.add(invoicePurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public AggregatedDetails getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        return null;
    }
}
