package com.flipkart.retail.analytics.service.aggregated;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.config.ReportsConfiguration;
import com.flipkart.retail.analytics.dto.AggregatedDetails;
import com.flipkart.retail.analytics.dto.OperationalPerformance;
import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.InvoicePurchasingTrend;
import com.flipkart.retail.analytics.enums.EntityType;
import com.flipkart.retail.analytics.enums.MetricType;
import com.flipkart.retail.analytics.persistence.InvoiceDao;
import com.flipkart.retail.analytics.persistence.entity.Invoice;
import com.flipkart.retail.analytics.service.AggregationService;
import com.flipkart.retail.analytics.utils.RetailAnalyticsUtils;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@EntityHandler(entityType = EntityType.INVOICE)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvoiceService implements AggregationService {
    private final InvoiceDao invoiceDao;
    private final RetailAnalyticsUtils retailAnalyticsUtils;
    private final ReportsConfiguration reportsConfiguration;

    @Override
    public List<PurchasingTrend> getAggregatedPurchasingTrend(List<String> vendorSites, List<String> warehouses) {
        String tableName = retailAnalyticsUtils.getTableName(reportsConfiguration.getInvoice());
        List<Invoice> invoices = invoiceDao.getInvoice(tableName, vendorSites, warehouses);
        List<PurchasingTrend> purchasingTrends = new ArrayList<>();
        for (Invoice invoice : invoices){
            InvoicePurchasingTrend invoicePurchasingTrend = new InvoicePurchasingTrend(invoice.getMonth(), invoice
                    .getCurrency(), invoice.getQuantity(), invoice.getAmount());
            purchasingTrends.add(invoicePurchasingTrend);
        }
        return purchasingTrends;
    }

    @Override
    public List<OperationalPerformance> getAggregatedOperationalPerformance(MetricType metricType, List<String> vendorSites, List<String> warehouses) {
        return null;
    }

    @Override
    public List<AggregatedDetails> getDetailedResponse(List<String> vendorSites, String fromMonth, String toMonth) {
        return null;
    }
}
