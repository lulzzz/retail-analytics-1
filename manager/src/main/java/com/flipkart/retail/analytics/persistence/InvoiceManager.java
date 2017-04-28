package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.InvoicePurchasingTrend;
import com.google.common.base.Joiner;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvoiceManager {
    private final JdbcTemplate jdbcTemplate;

    public List<PurchasingTrend> getInvoice(String tableName, List<String> vendorSites, List<String> warehouses) {
        String invoiceQuery = getInvoiceQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(invoiceQuery, new ResultSetExtractor<List<PurchasingTrend>>() {
            @Override
            public List<PurchasingTrend> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchasingTrend> invoiceList = new ArrayList<>();
                while (rs.next()) {
                    InvoicePurchasingTrend invoice = new InvoicePurchasingTrend(rs.getString(1), rs.getString(2), rs
                            .getLong(3), rs.getDouble(4));
                    invoiceList.add(invoice);
                }
                return invoiceList;
            }
        });
    }

    private String getInvoiceQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(total_quantity), SUM(total_invoice_amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }
}
